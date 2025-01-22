#!/usr/bin/env bash
source $HOME/miniconda/etc/profile.d/conda.sh
conda create -n ml python=3.8 -y
conda activate ml
pip install --upgrade transformers accelerate optimum pandas protobuf sentencepiece tokenizers torch bitsandbytes scipy gdown openai jsonlines notebook jupyter tabulate trl openai
