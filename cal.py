import sys


def getTrueLabel(fileName):
	id_label={}
	with open(fileName, "r") as f:
		f.readline()
		for line in f:
			s = line.split(",")
			id_label[s[0]] = s[1].strip()
			#mylist.append(s[1].strip())
	return id_label


def getPredLabel(fileName):
	id_pred={}

	with open(fileName, "r") as f:
		for line in f:
			s = line.split("\t")
			dataid = (s[0].split(","))[0].strip()
			id_pred[dataid] = s[1].strip()
	return id_pred
		


def getAccuracy(l1, l2):
	total = len(l1)
	accu = 0
	tp = 0
	tn = 0
	fp = 0
	fn = 0
	precision = 0
	recall = 0
 	f_score = 0

	for key in l1:
		true = l1.get(key)
		pred = l2.get(key)
		if(true == pred):
			accu = accu + 1
			if (true == "1"):
				tp = tp + 1
			if (true == "0"):
				tn = tn + 1
		else:
			if ((true == "1") and (pred == "0")):
				fn = fn + 1
			if ((true == "0") and (pred == "1")):
				fp = fp + 1
	precision = (tp * 1.0) / (tp + fp)
	recall = (tp * 1.0) / (tp + fn)
	f_score = (2.0 * precision * recall) / (precision + recall)

	return accu * 1.0 / total, tp, tn, fp, fn, precision, recall, f_score


def main():
	file1 = sys.argv[1]
	file2 = sys.argv[2]
	true = getTrueLabel(file1)
	pred = getPredLabel(file2)
	#print len(true)
	#print len(pred)
	accu, tp, tn, fp, fn, precision, recall, f_score = getAccuracy(true, pred)
	print "========================"
	print "Model Performace"
	print "Accuracy = " + str(accu)
	print "------------------------"
	print "Confusion Matrix"
	print "\t\tSurvived"
	print "Prediction\t" + "Yes\tNo"
	print "Yes\t\t" + str(tp) +"\t" + str(fp)
	print "No\t\t" + str(fn) +"\t" + str(tn)
	print "------------------------"

	print "Precision = " + str(precision)
	print "Recall = " + str(recall)
	print "F1 score = " + str(f_score)
	print "========================"
main()
