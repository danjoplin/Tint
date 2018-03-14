# Tint
Query-able mappings for all manner of data

Tint takes arbitrary data and produces multiple integer mappings, these mappings can be used for fast lookups and analysis.

Each Encoder will take some subset of columns and map that data to a single integer, these mappings can be of any form; a histogram, a Fisher's Discriminant Analysis (first dimension), PCA (principal component), random projection, etc.

The overall goal of this project is to build encoders which, on their own, are relatively simple and interpretable, but when combined yield high accuracy results in both classification and clustering problems. These Encoders could be a series of random projections to be used in Locality Sensitive Hashing for fast lookups, or OPTICS for clusutering through use of Random Projection Trees, or the first component of an LDA along with some random projections in a Random Forest. Regardless of the use the goal is to have it such that each encoder can be quickly, possibly visually, explained to a layman so that they might better understand more complex models than a Decision Tree, and gain a better understanding of how the decisions in a prediction or clustering model were made.
