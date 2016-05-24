package com.netflix.vms.transformer;

import java.util.Set;
import com.netflix.vms.transformer.common.TransformerMetricRecorder;
import com.netflix.vms.transformer.logger.TransformerServerLogger;
import java.util.function.Consumer;
import com.netflix.vms.transformer.common.TransformerContext;
import com.netflix.vms.transformer.common.TransformerFiles;
import com.netflix.vms.transformer.common.TransformerLogger;
import com.netflix.vms.transformer.common.TransformerPlatformLibraries;
import com.netflix.vms.transformer.common.publish.workflow.PublicationHistory;
import com.netflix.vms.transformer.common.publish.workflow.PublicationHistoryConsumer;
import com.netflix.vms.transformer.common.publish.workflow.TransformerCassandraHelper;

/**
 * Properties go here.
 *
 */
public class TransformerServerContext implements TransformerContext {

    /* dependencies */
    private final TransformerCassandraHelper poisonStatesHelper;
    private final TransformerCassandraHelper hollowValidationStats;
    private final TransformerCassandraHelper canaryResults;
    private final TransformerFiles files;
    private final TransformerPlatformLibraries platformLibraries;
    private final PublicationHistoryConsumer publicationHistoryConsumer;
    private final TransformerMetricRecorder metricRecorder;

    /* fields */
    private TransformerServerLogger logger;
    private long currentCycleId;
    private long now = System.currentTimeMillis();
    
    private Set<Integer> fastlaneIds;

    public TransformerServerContext(
            TransformerServerLogger logger,
            TransformerMetricRecorder metricRecorder,
            TransformerCassandraHelper poisonStatesHelper,
            TransformerCassandraHelper hollowValidationStats,
            TransformerCassandraHelper canaryResults,
            TransformerFiles files,
            TransformerPlatformLibraries platformLibraries,
            PublicationHistoryConsumer publicationHistoryConsumer) {
        this.logger = logger;
        this.metricRecorder = metricRecorder;
        this.poisonStatesHelper = poisonStatesHelper;
        this.hollowValidationStats = hollowValidationStats;
        this.canaryResults = canaryResults;
        this.files = files;
        this.platformLibraries = platformLibraries;
        this.publicationHistoryConsumer = publicationHistoryConsumer;
    }

    @Override
    public void setCurrentCycleId(long currentCycleId) {
        this.currentCycleId = currentCycleId;
        this.logger = logger.withCurrentCycleId(currentCycleId);
    }

    @Override
    public long getCurrentCycleId() {
        return currentCycleId;
    }

    @Override
    public void setNowMillis(long now) {
        this.now = now;
    }

    @Override
    public long getNowMillis() {
        return now;
    }

    @Override
	public void setFastlaneIds(Set<Integer> fastlaneIds) {
    	this.fastlaneIds = fastlaneIds;
    }

	@Override
	public Set<Integer> getFastlaneIds() {
		return fastlaneIds;
	}

	@Override
    public TransformerLogger getLogger() {
        return logger;
    }

    @Override
    public TransformerMetricRecorder getMetricRecorder() {
        return metricRecorder;
    }

    @Override
    public TransformerCassandraHelper getPoisonStatesHelper() {
        return poisonStatesHelper;
    }

    @Override
    public TransformerCassandraHelper getValidationStatsCassandraHelper() {
        return hollowValidationStats;
    }

    @Override
    public TransformerCassandraHelper getCanaryResultsCassandraHelper() {
        return canaryResults;
    }

    @Override
    public TransformerFiles files() {
        return files;
    }

    @Override
    public  TransformerPlatformLibraries platformLibraries() {
        return platformLibraries;
    }

    @Override
    public Consumer<PublicationHistory> getPublicationHistoryConsumer() {
        return publicationHistoryConsumer;
    }
}
