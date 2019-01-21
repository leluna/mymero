(ns figwheel.main.generated.dev-auto-test-runner
  (:require [cljs.test :refer-macros [run-tests]]
            [cljs-test-display.core] [mymero.core-test]))

  (run-tests (cljs-test-display.core/init! "app-auto-testing") (quote mymero.core-test))