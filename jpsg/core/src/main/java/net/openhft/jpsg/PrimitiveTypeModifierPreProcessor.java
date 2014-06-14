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

package net.openhft.jpsg;

import java.util.Map;
import java.util.function.UnaryOperator;


public class PrimitiveTypeModifierPreProcessor extends TemplateProcessor {
    public static final int PRIORITY = OptionProcessor.PRIORITY + 1;

    private final String keyword;
    private final UnaryOperator<PrimitiveType> typeMapper;

    public PrimitiveTypeModifierPreProcessor(String keyword,
            UnaryOperator<PrimitiveType> typeMapper) {
        this.keyword = keyword;
        this.typeMapper = typeMapper;
    }

    @Override
    protected int priority() {
        return PRIORITY;
    }

    @Override
    protected void process(StringBuilder sb, Context source, Context target, String template) {
        String modifier = OptionProcessor.modifier(keyword);
        for (Map.Entry<String, Option> e : source) {
            String dim = e.getKey();
            Option targetT = target.getOption(dim);
            if (e.getValue() instanceof PrimitiveType && targetT instanceof PrimitiveType) {
                PrimitiveType sourceT = (PrimitiveType) e.getValue();
                String kwDim = dim + "." + keyword;
                if (typeMapper.apply((PrimitiveType) targetT) != targetT) {
                    String modP = OptionProcessor.prefixPattern(modifier, sourceT.standalone);
                    template = template.replaceAll(modP, IntermediateOption.of(kwDim).standalone);
                }
                if (typeMapper.apply(sourceT) != sourceT) {
                    template = typeMapper.apply(sourceT).intermediateReplace(template, kwDim);
                }
            }
        }
        // remove left modifier templates when for example target is object
        template = template.replaceAll(modifier, "");
        postProcess(sb, source, target, template);
    }
}
