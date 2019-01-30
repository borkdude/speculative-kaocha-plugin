<img src="https://raw.githubusercontent.com/borkdude/speculative/master/logo/favicon-160.png">

# speculative kaocha plugin

This [kaocha](https://github.com/lambdaisland/kaocha) plugin instruments tests
with [speculative](https://github.com/borkdude/speculative) clojure.core specs.

## Installation

Include this plugin as a dependency. E.g. for `tools.deps.alpha`:

``` clojure
{:aliases
  {:test
    {:extra-deps
      {lambdaisland/kaocha {:mvn/version "0.0-389"}
       speculative/kaocha-plugin {:git/url "https://github.com/borkdude/speculative-kaocha-plugin"
                                  :sha "72e9fedaa15cca0384e055d551e695d6a2e27a53"}}}}}
```

A Clojars release will be made available soon. If interested, feel free to
remind [@borkdude](https://twitter.com/borkdude) on Clojurians Slack or Twitter.

## Configuration

Add `:speculative-kaocha.plugin/instrument` to the `:plugins` key in
`tests.edn`. 

By default `clojure.core` functions are instrumented. This can be disabled by
setting `:speculative-kaocha.plugin/no-instrument` to `true` on a per test suite
basis.

``` clojure
#kaocha/v1
{:plugins [:speculative-kaocha.plugin/instrument]
 :tests [{:id :unit
          :kaocha/test-paths    ["test/unit"]}
         {:id :integration
          :speculative-kaocha.plugin/no-instrument true
          :kaocha/test-paths    ["test/integration"]}]}
```

## Demo

``` clojure
$ cat test/demo/core_test.cljc
(ns demo.core-test
  (:require [clojure.test :as t]))

(t/deftest foo
  (t/is (flatten 1)))

$ clojure -A:test -m kaocha.runner
[(.)]
1 tests, 1 assertions, 0 failures.

$ clojure -A:test -m kaocha.runner --plugin speculative-kaocha.plugin/instrument
[(E)]
Randomized with --seed 1206313428

ERROR in demo.core-test/foo (alpha.clj:132)
Exception: clojure.lang.ExceptionInfo: Call to #'clojure.core/flatten did not conform to spec.
{:clojure.spec.alpha/problems [{:path [:x :clojure.spec.alpha/pred], :pred clojure.core/sequential?, :val 1, :via [:speculative.specs/sequential], :in [0]} {:path [:x :clojure.spec.alpha/nil], :pred nil?, :val 1, :via [], :in [0]}], :clojure.spec.alpha/spec #object[clojure.spec.alpha$regex_spec_impl$reify__2509 0x76596288 "clojure.spec.alpha$regex_spec_impl$reify__2509@76596288"], :clojure.spec.alpha/value (1), :clojure.spec.alpha/fn clojure.core/flatten, :clojure.spec.alpha/args (1), :clojure.spec.alpha/failure :instrument, :clojure.spec.test.alpha/caller {:file "core.clj", :line 665, :var-scope clojure.core/apply}}
```

## Tests

    clojure -A:test -m kaocha.runner

## License

Copyright © 2018 Michiel Borkent

Distributed under the EPL License, same as Clojure. See LICENSE.
