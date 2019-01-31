(ns speculative-kaocha.plugin
  (:require [kaocha.plugin :refer [defplugin]]
            [speculative.instrument :refer [instrument unstrument]]))

(defn test-suite?
  "TODO: this function can be replaced using suite? from this commit
  https://github.com/lambdaisland/kaocha/commit/76460da1ae702af29f7b9c402211368b1fa9b7aa
  once it is released."
  [test test-plan]
  (contains?
   (into #{}
         (map :kaocha.testable/id)
         (:kaocha.test-plan/tests test-plan))
   (:kaocha.testable/id test)))

(defplugin ::instrument
  (pre-test [test test-plan]
    (when (and (test-suite? test test-plan)
               (not (::no-instrument test)))
      (instrument))
    test)

  (post-test [test test-plan]
    (when (test-suite? test test-plan)
      (unstrument))
    test))
