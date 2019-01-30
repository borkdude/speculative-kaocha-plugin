(ns speculative_kaocha.unit
  (:require
   [clojure.test :as t :refer [deftest is are testing]]
   [respeced.test :refer [caught?]]))

(deftest instrumented-test
  (is (caught? `flatten (flatten 1))))
