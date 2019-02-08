(defproject speculative/kaocha-plugin "0.0.1"
  :description "Kaocha plugin which instruments speculative specs during testing"
  :url "https://github.com/borkdude/speculative-kaocha-plugin"
  :scm {:name "git"
        :url "https://github.com/borkdude/speculative-kaocha-plugin"}
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.10.0"]
                 [speculative "0.0.3"]
                 [lambdaisland/kaocha "0.0-389" :scope "provided"]
                 [lambdaisland/kaocha-cljs "0.0-16" :scope "provided"]]
  :profiles {:kaocha {:dependencies
                      [[respeced "0.0.1"]
                       [org.clojure/clojurescript "1.10.516"]
                       [org.clojure/test.check "0.9.0"]]}}
  :aliases {"kaocha" ["with-profile" "+kaocha" "run" "-m" "kaocha.runner"]}
  :deploy-repositories [["clojars" {:url "https://clojars.org/repo"
                                    :username :env/clojars_user
                                    :password :env/clojars_pass
                                    :sign-releases false}]])
