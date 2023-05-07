GRADLE ?= gradle
GRADLE_TEST ?= $(GRADLE) test
GRADLE_TEST_DEFAULT_FLAGS ?= --rerun-tasks
GRADLE_TEST_EXTRA_FLAGS ?=

test:
	$(GRADLE_TEST) $(GRADLE_TEST_DEFAULT_FLAGS) $(GRADLE_TEST_EXTRA_FLAGS)

test/time/stress:
	./test/stress_test/run.sh \
	  1 \
	  10 \
    1000000 \
    StressTestTime \
    test/stress_test/results/time

test/memory/stress:
	./test/stress_test/run.sh \
	  1 \
	  10 \
    1000000 \
    StressTestMemory \
    test/stress_test/results/memory
