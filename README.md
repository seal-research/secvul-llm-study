# Artifact for LLM Study

# Installation

Use `setup_conda.sh` to install miniconda if not installed yet

Use `setup.sh` to create a conda environment named `ml` and install all dependencies.


# Directory structure
- `appendix.pdf`: Contains the appendix of our paper
- `data/`: Contains code for loading all datasets
- `data/prompt.py`: Contains all system and user prompts used for different LLMs.
- `models/`: Code for loading different LLMs
- `models/config.py`: Default data paths and configurations for LLMs (e.g., temperature hyper-param)
- `utils/`: Some common utilties for metrics
- `utils/metrics_test.py`: Given an output folder of results, computes all metrics in a tabular format (+latex)
- `main.py`: Runs a given benchmark with an LLM and computes results

# Datasets

This artifact contains CVEFixes Java and C/C++, Juliet Java and C/C++, and OWASP datasets.

