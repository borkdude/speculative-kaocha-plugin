(ns speculative-kaocha.no-instrument
  (:require [clojure.test :as t :refer [deftest is are testing]]))

(deftest not-instrumented-test
  (testing "this test doesn't crash because of speculative's flatten spec"
      (is (nil? (dotimes [i 1000]
                  (flatten 1))))))
