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

import io.reactiverse.pgclient.impl.ArrayTuple;
import io.vertx.codegen.annotations.Fluent;
import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;

import java.util.List;

/**
 * A prepared query.
 *
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
@VertxGen
public interface PgPreparedQuery {

  /**
   * @return create a query with no arguments
   */
  @Fluent
  default PgPreparedQuery execute(Handler<AsyncResult<PgResult<Row>>> handler) {
    return execute(ArrayTuple.EMPTY, handler);
  }

  /**
   * Create a cursor with the provided {@code arguments}.
   *
   * @param args the list of arguments
   * @return the query
   */
  @Fluent
  PgPreparedQuery execute(Tuple args, Handler<AsyncResult<PgResult<Row>>> handler);

  /**
   * @return create a query cursor with a {@code fetch} size and empty arguments
   */
  default PgCursor cursor() {
    return cursor(ArrayTuple.EMPTY);
  }

  /**
   * Create a cursor with the provided {@code arguments}.
   *
   * @param args the list of arguments
   * @return the query
   */
  PgCursor cursor(Tuple args);

  /**
   * Execute the prepared query with a cursor and createStream the result. The createStream opens a cursor
   * with a {@code fetch} size to fetch the results.
   * <p/>
   * Note: this requires to be in a transaction, since cursors require it.
   *
   * @param fetch the cursor fetch size
   * @param args the prepared query arguments
   * @return the createStream
   */
  PgStream<Row> createStream(int fetch, Tuple args);

  /**
   * Execute a batch.
   *
   * @param argsList the list of tuple for the batch
   * @return the createBatch
   */
  @Fluent
  PgPreparedQuery batch(List<Tuple> argsList, Handler<AsyncResult<PgResult<Row>>> handler);

  /**
   * Close the prepared query and release its resources.
   */
  void close();

  /**
   * Like {@link #close()} but notifies the {@code completionHandler} when it's closed.
   */
  void close(Handler<AsyncResult<Void>> completionHandler);

}
