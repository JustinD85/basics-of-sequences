(ns custom-functions.core
  (:gen-class))

;; Defintion of `Sequence`: an ordered collection of elements

;; The function `map`'s essential behaviour is to derive a new value `y` from
;; an existing sequence `x`

;; Using a function `f` I can show this as follows: y1 = f(x1) ... yn = f(xn)

;; Any data structure in Clojure that responds to the functions `first` `rest`
;; and `cons` is considered to implement the sequence abstraction

;; I will implement a simple linked list of nodes to show understanding
(def node-5 {:value "lamb" :next nil})
(def node-4 {:value "little" :next node-5})
(def node-3 {:value "a" :next node-4})
(def node-2 {:value "had" :next node-3})
(def node-1 {:value "Mary" :next node-2})

;; I implement the three core functions that all sequences should respond to

;; NOTE these are modified to work with maps,
;; normally tuples would be returned from maps
;; so I am going to write custom implementation to play nicely with maps

;; This function returns the first value
(def _first
  (fn [node]
    (:value node)))

;; This function returns the next node
(def _rest
  (fn [node]
    (:next node)))

;; This function prepends a node to an existing node
(def _cons
  (fn [new-node-value node]
    {:value new-node-value :next node}))

;;Example usages
;; Load a REPL to try them, or copy into online Clojure REPL

;; returns "Mary"
(_first node-1)

;; returns "had"
(_first (_rest node-1))

;; returns "a"
(_first (_rest (_rest node-1)))

;; returns "Ohhhhhh,"
(def node-0 (_cons "Ohhhhhh" node-1))
(_first node-0)

;; returns "Mary"
(_first (_rest node-0))

;; Implementation of map

;; Explanation: _map transforms the list given according to the transform function
;; this happens recursively until _map reaches the end of the list.
;; So the final map is built from the last call of _maps return value
;; and that is given to a cons to continue building out the final result from
;; the end of the map to the front by prepending nodes. #mind-blown

(defn _map [list transform-function]
  (when list
    (_cons (transform-function (_first list)) (_map (_rest list) transform-function))))

;; _map in action returns all value uppercased, "OHHHHHH, MARY HAD A LITTLE LAMB"
(_map node-1 #(.toUpperCase %))

;; Examples of interesting usages of functions
;; normally with map/filter/etc.. you give map a sequence of values.. what if instead
;; we give the the functions a sequence of functions that operate on another sequence of values!

;; some usual functions in a program
(def sum #(reduce + %))
(def avg #(/ (sum %) (float (count %))))

;; an unusual implementation, here we give map all the functions that returns stats we care
;; about giving them one set of values, much better than calling function 123...n times!

(defn stats [list-numbers]
  (map #(% list-numbers) [sum count avg])) ;;count is built in

(stats [13 1337 42])
;; => (1392 3 464.0)

;; That's right instead of calling 3 different functions on the same values, we passed map
;; a sequence of functions to be called on ONE value!! schwaeeet


;; Since keywords can be used as functions I can feed them to map to succinctly get values

;; Our super dataset
(def identities
  [{:cover "Superman" :but-really "Clark Kent"}
   {:cover "Santa Klaus" :but-really "Your Father"}
   {:cover "Boogey Man" :but-really "Your Brother"}
   {:cover "Spider Man" :but-really "Peter Parker"}])

;; Example using a keyword
(map :but-really identities)
;; => ("Clark Kent" "Your Father" "Your Brother" "Peter Parker")

;; Example using reduce to adjust values of a map
(defn decrementer [_map [key value]]
  (assoc _map key (dec value)))

(reduce decrementer {} {:fav-num 14 :age 28 :height 71})
;; => {:fav-num 13, :age 27, :height 70}

;; which is functionally the same as
(assoc (assoc (assoc {} :fav-num (dec 14))
              :age (dec 28))
       :height (dec 71))
;; => {:fav-num 13, :age 27, :height 70}

;; Example implementation of `take`
(defn _take [number sequence]
  (if (> number 0)
    (cons (first sequence) (_take (dec number) (rest sequence)))
    ()))

;; confirm naive implementation
(_take 0 (range 50))
;; => ()
(take 0 (range 50))
;; => ()

(_take 2 (range 50))
;; => (0 1)
(_take 2 (range 50))
;; => (0 1)

(_take 15 (range 50))
;; => (0 1 2 3 4 5 6 7 8 9 10 11 12 13 14)
(take 15 (range 50))
;; => (0 1 2 3 4 5 6 7 8 9 10 11 12 13 14)

;; Example implementation of `drop`
(defn _drop [number sequence]
  (if (> number 0)
    (_drop (dec number) (rest sequence))
    (reverse (into () sequence))))

;; confirm naive implementation
(_drop 0 [1 2 3])
;; => (1 2 3)
(drop 0 [1 2 3])
;; => (1 2 3)

(_drop 5 (range 12))
;; => (5 6 7 8 9 10 11)
(drop 5 (range 12))
;; => (5 6 7 8 9 10 11)

(_drop 2 [1])
;; => ()
(drop 2 [1])
;; => ()
