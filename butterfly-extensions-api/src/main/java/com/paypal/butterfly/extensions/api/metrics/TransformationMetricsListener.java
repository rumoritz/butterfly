package com.paypal.butterfly.extensions.api.metrics;

import java.util.List;

/**
 * Spring beans that implement this interface will be able
 * to be notified about metrics generated by any transformation
 *
 * @author facarvalho
 */
public interface TransformationMetricsListener {

    void notify(List<TransformationMetrics> metricsList);

}
