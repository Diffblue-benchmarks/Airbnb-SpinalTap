/**
 * Copyright 2019 Airbnb. Licensed under Apache-2.0. See License in the project root for license
 * information.
 */
package com.airbnb.spinaltap;

import com.airbnb.spinaltap.common.source.SourceState;
import com.airbnb.spinaltap.common.util.StateRepositoryFactory;
import com.airbnb.spinaltap.common.util.ZookeeperRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import java.util.Collection;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.curator.framework.CuratorFramework;

/** Represents an implement of {@link StateRepositoryFactory} in Zookeeper. */
@RequiredArgsConstructor
public final class ZookeeperRepositoryFactory implements StateRepositoryFactory {
  @NonNull private final CuratorFramework zkClient;

  @Override
  public ZookeeperRepository<SourceState> getStateRepository(String sourceName, String parition) {
    return new ZookeeperRepository<>(
        zkClient,
        String.format("/spinaltap/pipe/%s/state", sourceName),
        new TypeReference<SourceState>() {});
  }

  @Override
  public ZookeeperRepository<Collection<SourceState>> getStateHistoryRepository(
      String sourceName, String partition) {
    return new ZookeeperRepository<>(
        zkClient,
        String.format("/spinaltap/pipe/%s/state_history", sourceName),
        new TypeReference<Collection<SourceState>>() {});
  }
}
