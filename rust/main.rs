use rand::Rng;
use std::fs::File;
use std::io::{prelude::*, BufReader};
use lp_modeler::solvers::{CbcSolver, SolverTrait};
use lp_modeler::dsl::*;
use lp_modeler::constraint;

#[derive(Clone)]
struct Item {
    len: u32
}

#[derive(Clone)]
struct DataSet {
    bin_capacity: u32,
    items: Vec<Item>
}

impl DataSet {
    fn random(bin_capacity: u32, max_len: u32, count: u32) -> DataSet {
        let mut dataset = DataSet {
            bin_capacity: bin_capacity,
            items: Vec::new()
        };
        let mut rng = rand::thread_rng();
        for _ in 0..count {
            dataset.items.push(Item {
                len: rng.gen_range(1..(max_len + 1))
            });
        }
        dataset
    }

    fn load_from_file(filename: &str) -> DataSet {
        // Load file
        let file = File::open(filename).unwrap();
        let reader = BufReader::new(file);
        let mut lines_iter = reader.lines().map(|l| l.unwrap());
        // Read first line
        let first_line = lines_iter.next().unwrap();
        let tokens: Vec<&str> = first_line.split_whitespace().collect();
        let bin_capacity = tokens[0].parse::<u32>().unwrap();
        let item_count = tokens[1].parse::<u32>().unwrap();
        // Create dataset
        let mut dataset = DataSet {
            bin_capacity: bin_capacity,
            items: Vec::with_capacity(item_count as usize)
        };
        // Read items
        for _ in 1..item_count {
            dataset.items.push(Item {
                len: lines_iter.next().unwrap()
                    .parse::<u32>().unwrap()
            });
        }
        dataset
    }

    fn lower_born(&self) -> u32 {
        let sum: u32 = self.items.iter().map(|i| i.len).sum(); 
        if self.bin_capacity != 1 {
            (sum + 1) / self.bin_capacity
        } else {
            sum / self.bin_capacity
        }
    }

    fn upper_born(&self) -> u32 {
        self.items.len() as u32
    }
}

#[derive(Clone)]
struct Bin {
    capacity: u32,
    items: Vec<Item>
}

impl Bin {
    fn new(capacity: u32) -> Bin {
        Bin {
            capacity: capacity,
            items: Vec::with_capacity(capacity as usize)
        }
    }

    fn total_len(&self) -> u32 {
        self.items.iter().map(|i| i.len).sum()
    }

    fn remaining_len(&self) -> u32 {
        self.capacity - self.total_len()
    }
}

#[derive(Clone)]
struct Solution {
    bin_capacity: u32,
    bins: Vec<Bin>
}

impl Solution {
    fn lp_solution(dataset: &DataSet) -> Solution {
        let bin_count = dataset.upper_born() as usize;
        let item_count = dataset.items.len() as usize;

        // Create variables
        // i => item
        // j => bin
        let mut variables: Vec<LpBinary> = Vec::with_capacity(bin_count * item_count);
        for i in 0..item_count {
            for j in 0..bin_count {
                variables.push(LpBinary::new(format!("{}_{}", i, j)));
            }
        }

        // Define problem
        let mut problem = LpProblem::new("LP Solution", LpObjective::Minimize);

        // Define constraints
        for j in 0..bin_count {
            problem += sum(&variables)
        }

        // Create
        Solution {
            bin_capacity: dataset.bin_capacity,
            bins: Vec::new()
        }
    }   

    fn first_fit_decreasing(dataset: &DataSet) -> Solution {
        // Initialize solution (empty)
        let mut solution = Solution {
            bin_capacity: dataset.bin_capacity, 
            bins: Vec::new()
        };
        // Create sorted item list
        let mut sorted = dataset.items.to_vec();
        sorted.sort_by(|a, b| b.len.cmp(&a.len));
        // Add items to the solution
        'item_loop: for item in sorted {
            assert!(item.len <= dataset.bin_capacity);
            // Check for a bin with enough space
            for bin in solution.bins.iter_mut() {
                if item.len <= bin.remaining_len() {
                    bin.items.push(Item {len: item.len});
                    continue 'item_loop;
                }
            }
            // No bin available, create a new one
            let mut bin = Bin::new(dataset.bin_capacity);
            bin.items.push(Item {len: item.len});
            solution.bins.push(bin);
        }
        solution
    }

    fn fitness(&self) -> u32 {
        self.bins.len() as u32
    }
}

fn main() {
    let dataset = DataSet::load_from_file("data/binpack1d_00.txt");
    let solution = Solution::first_fit_decreasing(&dataset);
    
    println!("Lower born: {}", dataset.lower_born());
    println!("Capacity: {}", solution.bin_capacity);
    for bin in &solution.bins {
        print!("{}/{}: ", bin.total_len(), bin.capacity);
        for item in &bin.items {
            print!("{} ", item.len);
        }
        println!();
    }
    println!("Fitness : {}", solution.fitness());
}
