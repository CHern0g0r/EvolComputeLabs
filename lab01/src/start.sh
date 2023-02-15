TEST_FILE="../test/test.csv"

# Compile cython
python setup.py build_ext --inplace

# generate tests
python gen_test.py $TEST_FILE --n 100000

# run tests
python run_test.py --path $TEST_FILE --compare