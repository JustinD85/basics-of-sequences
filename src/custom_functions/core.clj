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
