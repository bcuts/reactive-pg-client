/*
 * Copyright (C) 2017 Julien Viet
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package io.reactiverse.pgclient;

import io.vertx.codegen.annotations.Fluent;
import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.*;

import java.util.List;

/**
 * A connection to Postgres.
 *
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 * @author <a href="mailto:emad.albloushi@gmail.com">Emad Alblueshi</a>
 */
@VertxGen
public interface PgConnection extends PgClient {

  /**
   * Create a prepared query.
   *
   * @param sql the sql
   * @param handler the handler notified with the prepared query asynchronously
   */
  @Fluent
  PgConnection prepare(String sql, Handler<AsyncResult<PgPreparedQuery>> handler);

  /**
   * Set an handler called with connection errors.
   *
   * @param handler the handler
   * @return a reference to this, so the API can be used fluently
   */
  @Fluent
  PgConnection exceptionHandler(Handler<Throwable> handler);

  /**
   * Set an handler called when the connection is closed.
   *
   * @param handler the handler
   * @return a reference to this, so the API can be used fluently
   */
  @Fluent
  PgConnection closeHandler(Handler<Void> handler);

  /**
   * Begin a transaction and returns a {@link PgTransaction} for controlling and tracking
   * this transaction.
   * <p/>
   * When the connection is explicitely closed, any inflight transaction is rollbacked.
   *
   * @return the transaction instance
   */
  PgTransaction begin();

  /**
   * Set an handler called when the connection receives notification on a channel.
   * <p/>
   * The handler is called with the {@link PgNotification} and has access to the channel name
   * and the notification payload.
   *
   * @param handler the handler
   * @return the transaction instance
   */
  @Fluent
  PgConnection notificationHandler(Handler<PgNotification> handler);

  /**
   * @return whether the connection uses SSL
   */
  boolean isSSL();

  /**
   * Close the current connection after all the pending commands have been processed.
   */
  void close();

  @Override
  PgConnection preparedQuery(String sql, Handler<AsyncResult<PgResult<Row>>> handler);

  @Override
  PgConnection query(String sql, Handler<AsyncResult<PgResult<Row>>> handler);

  @Override
  PgConnection preparedQuery(String sql, Tuple arguments, Handler<AsyncResult<PgResult<Row>>> handler);

  @Override
  PgConnection preparedBatch(String sql, List<Tuple> batch, Handler<AsyncResult<PgResult<Row>>> handler);

}
