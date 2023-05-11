GRADLE ?= gradle
GRADLE_TEST ?= $(GRADLE) test
GRADLE_TEST_DEFAULT_FLAGS ?= --rerun-tasks
GRADLE_TEST_EXTRA_FLAGS ?=

TEST_STRESS_MIN_LOGS ?= 1
TEST_STRESS_STEP ?= 10
TEST_STRESS_MAX_LOGS ?= 1000000
TEST_STRESS_NUM_SIMULS ?= 10

TEST_PGLOGS_PATH ?= pglogs.log

test:
	$(GRADLE_TEST) $(GRADLE_TEST_DEFAULT_FLAGS) $(GRADLE_TEST_EXTRA_FLAGS)

test/stress/wout-lushu/log-generator:
	./test/stress_test/run_wout_loggenerator.sh \
	  $(TEST_STRESS_MIN_LOGS) \
	  $(TEST_STRESS_STEP) \
    $(TEST_STRESS_MAX_LOGS) \
    $(TEST_STRESS_NUM_SIMULS) \
    test/stress_test/results/wout-lushu/log-generator

test/stress/wout-lushu/pglogs:
	./test/stress_test/run_wout_logfile.sh \
	  $(TEST_STRESS_MIN_LOGS) \
	  $(TEST_STRESS_STEP) \
    $(TEST_STRESS_MAX_LOGS) \
    $(TEST_STRESS_NUM_SIMULS) \
    test/stress_test/results/wout-lushu/pglogs \
    $(TEST_PGLOGS_PATH)

test/stress/with-lushu/log-generator:
	./test/stress_test/run_with_loggenerator.sh \
	  $(TEST_STRESS_MIN_LOGS) \
	  $(TEST_STRESS_STEP) \
    $(TEST_STRESS_MAX_LOGS) \
    $(TEST_STRESS_NUM_SIMULS) \
    test/stress_test/results/with-lushu/log-generator

test/stress/with-lushu/pglogs:
	./test/stress_test/run_with_logfile.sh \
	  $(TEST_STRESS_MIN_LOGS) \
	  $(TEST_STRESS_STEP) \
    $(TEST_STRESS_MAX_LOGS) \
    $(TEST_STRESS_NUM_SIMULS) \
    test/stress_test/results/with-lushu/pglogs \
    $(TEST_PGLOGS_PATH)

# This test tries to emulate what Zhefuscator (Saffran et. al) did. Zhefuscator
# only recognized integer numbers as an aggregate entity.
test/emulate/zhe:
	gradle fatjar
	temp="$$(mktemp)" && \
	head -1000 test/stress_test/pglogs | java -jar ./Lushu/build/libs/Lushu.jar \
    example/zhe.yaml \
    "$$temp" \
    true \
    1>/dev/null

	temp="$$(mktemp)" && \
	head -1000 test/stress_test/pglogs | java -jar ./Lushu/build/libs/Lushu.jar \
    example/config.yaml \
    "$$temp" \
    true \
    1>/dev/null
