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

package io.reactiverse.pgclient.impl.codec.decoder.message;

import io.reactiverse.pgclient.impl.codec.TxStatus;
import io.reactiverse.pgclient.impl.codec.decoder.InboundMessage;

/**
 *
 * <p>
 * The frontend can issue commands. Every message returned from the backend has transaction status
 * that would be one of the following
 *
 * <p>
 * IDLE : Not in a transaction block
 * <p>
 * ACTIVE : In transaction block
 * <p>
 * FAILED : Failed transaction block (queries will be rejected until block is ended)
 *
 * @author <a href="mailto:emad.albloushi@gmail.com">Emad Alblueshi</a>
 *
 */

public enum ReadyForQuery implements InboundMessage {

  IDLE(TxStatus.IDLE),
  ACTIVE(TxStatus.ACTIVE),
  FAILED(TxStatus.FAILED);

  private final TxStatus txStatus;

  ReadyForQuery(TxStatus txStatus) {
    this.txStatus = txStatus;
  }

  public TxStatus txStatus() {
    return txStatus;
  }

  private static final byte I = (byte) 'I', T = (byte) 'T';

  public static ReadyForQuery decode(byte id) {
    if (id == I) {
      return ReadyForQuery.IDLE;
    } else if (id == T) {
      return ReadyForQuery.ACTIVE;
    } else {
      return ReadyForQuery.FAILED;
    }
  }

  @Override
  public String toString() {
    return "ReadyForQuery{txStatus=" + name() + "}";
  }
}
