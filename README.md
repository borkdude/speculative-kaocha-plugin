<img src="https://raw.githubusercontent.com/borkdude/speculative/master/logo/favicon-160.png">

# speculative kaocha plugin

[![Clojars Project](https://img.shields.io/clojars/v/speculative/kaocha-plugin.svg)](https://clojars.org/speculative/kaocha-plugin)
[![cljdoc badge](https://cljdoc.org/badge/speculative/kaocha-plugin)](https://cljdoc.org/d/speculative/kaocha-plugin/CURRENT)

This [kaocha](https://github.com/lambdaisland/kaocha) plugin instruments tests
with [speculative](https://github.com/borkdude/speculative) clojure.core specs.

## Installation

### Tools.deps

``` clojure
{:deps {speculative/kaocha-plugin {:mvn/version "0.0.1"}}}
```

### Leiningen / Boot

``` clojure
[speculative/kaocha-plugin "0.0.1"]
```

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

## Selectively disabling specs

In the unfortunate event that a speculative spec is wrong, or your code is
offending a spec and you don't want to fix it right away, you can selectively
disable specs by `def`'ing them to `nil` after loading `speculative.instrument`.

E.g. `(s/def clojure.core/re-find nil)`.

In `deps.edn` this can be done as follows:

``` clojure
:main-opts
["-e" "(require,'[speculative.instrument])"
 "-e" "(require,'[clojure.spec.alpha,:as,s])"
 "-e" "(s/def,clojure.core/re-find,nil)"
 "-m" "kaocha.runner" "--no-capture-output"]
```

## Tests

    clojure -A:test
    lein kaocha

## License

Copyright © 2018 Michiel Borkent

Distributed under the EPL License, same as Clojure. See LICENSE.
