/*
 * Copyright 2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.koloboke.jpsg.collect.algo.hash;

import com.koloboke.jpsg.BitsModifierPostProcessor;
import com.koloboke.jpsg.PrimitiveTypeModifierPostProcessor;


public final class TableTypePostProcessor extends PrimitiveTypeModifierPostProcessor {

    public TableTypePostProcessor() {
        super("tt", TableType.INSTANCE, TableTypeDimFilter.INSTANCE);
    }

    @Override
    public int priority() {
        return BitsModifierPostProcessor.getPRIORITY() + 5;
    }
}
