package org.openalgo.analytics;

import org.apache.commons.math3.stat.StatUtils;
import org.apache.commons.math3.stat.correlation.Covariance;

import java.util.List;

public class CovarianceCall {

    public static double getCovariance(final List<Double> list1, final List<Double> list2) throws Exception {
        if(list1.size() == list2.size()) {
            double[] double1 = new double[list1.size()];
            double[] double2 = new double[list1.size()];
            for(int i=0; i< list1.size(); i++) {
                double1[i] = list1.get(i);
                double2[i] = list2.get(i);
            }
            Covariance covariance = new Covariance();

            return covariance.covariance(StatUtils.normalize(double1), StatUtils.normalize(double2));

        } else {
            throw new Exception("Input arrays size do not match");
        }
    }
}
