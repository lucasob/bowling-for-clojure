# Scoring Bowling with Clojure

## What

This code will simply score a complete game of bowling.

### Info & Representation

* A second roll in a frame of a strike is represented with `nil`
* Rolls into the gutter / misses are represented as 0
* Generally, the number of pins knocked down represents a roll

## Why

This was an interview question I was recently asked, and originally performed in TypeScript. Given the role (hehe) is
for a clojure team, I figured it's time to start learning.

## Commentary

* Clojure is weird and wonderful.
* If you want to do something, there's likely already a built-in for it, you just have to find it.
* Trying to keep the code-as-data mentality is rather difficult in a problem that is more easily solved imperatively.
  However, once I understood this problem, it became easier (read: less difficult) to write out the score of a game as
  an expression


# Invocation and Replication
* At the time of cracking onto this, this was on clojure 1.10.1 with openjdk 16.0.2.