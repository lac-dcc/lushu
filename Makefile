GRADLE ?= gradle
GRADLE_TEST ?= $(GRADLE) test
GRADLE_TEST_DEFAULT_FLAGS ?= --rerun-tasks
GRADLE_TEST_EXTRA_FLAGS ?=

test:
	$(GRADLE_TEST) $(GRADLE_TEST_DEFAULT_FLAGS) $(GRADLE_TEST_EXTRA_FLAGS)

test/time/fullspeed:
	./test/scripts/test_time_overhead_full_speed.sh 1 10 1000000
