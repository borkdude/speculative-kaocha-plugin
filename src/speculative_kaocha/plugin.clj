(ns speculative-kaocha.plugin
  (:require [kaocha.plugin :refer [defplugin]]
            [speculative.instrument :refer [instrument unstrument]]))

(defn test-suite? [test]
  (contains? #{:kaocha.type/clojure.test
               :kaocha.type/cucumber
               :kaocha.type/cljs}
             (:kaocha.testable/type test)))

(defplugin ::instrument
  (pre-test [test test-plan]
            (when (and (test-suite? test)
                       (not (::no-instrument test)))
              (instrument))
            test)
  (post-test [test test-plan]
             (when (test-suite? test)
               (unstrument))
             test))
