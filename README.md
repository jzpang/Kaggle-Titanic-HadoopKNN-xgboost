# Kaggle-Titanic-KNN-on-Hadoop
A Hadoop MapReduce KNN model on Titanic Dataset

Introduction
This is a Hadoop Mapreduce KNN algorithm training on Titanic dataset, feature engineering was performed to improve the performance of kNN.


Run:
1. First run fe.ipynb on Jupyter notebook for feature engineering
2. hadoop jar KNNClassifier.jar KNNDriver [Training_Data_Path] [Test_Data_Path] [Output_Path] [k] [continuous_feature_index]
Note: where continuous_feature_index is a list of feature index that you want to use as continuous variables. It there is no continuous variables, input “NULL”.
      [k] is the number of neiborhoods used in KNN
3. python cal.py gender_submission.csv [your_prediction_file] 
Analyze your prediction and the true lables for accuracy and confusion matrix, etc
Note: [your_prediction_file] is a part-r-00000 file

