# SelectVC-POP
The source code of POP to detect outliers in high-dimensional categorical data published in CIKM17.

Paper title: Selective Value Coupling Learning for Detecting Outliers in High-dimensional Categorical Data.


Please modify the path in SEAS pakage ODUtils class to run POP on your data sets. 
The String varient "path" can either be a single data set file (should be .arff format) or a folder containing mutiple data sets.
The only parameter of POP is selective rate, 0.3 is recommanded in paper. You can also try other possible rate by setting varient "rate".

Except AUC result and execution time, the result of data indicator "coup" is also demonstrated in the console.  

Please cite our paper if the source code can help you.

Pang, G., Xu, H., Cao, L., & Zhao, W. (2017, November). Selective Value Coupling Learning for Detecting Outliers in High-Dimensional Categorical Data. In Proceedings of the 2017 ACM on Conference on Information and Knowledge Management (pp. 807-816). ACM.

BibTex:
@inproceedings{pang2017selective,
  title={Selective Value Coupling Learning for Detecting Outliers in High-Dimensional Categorical Data},
  author={Pang, Guansong and Xu, Hongzuo and Cao, Longbing and Zhao, Wentao},
  booktitle={Proceedings of the 2017 ACM International Conference on Information and Knowledge Management},
  pages={807--816},
  year={2017},
  organization={ACM}
}
