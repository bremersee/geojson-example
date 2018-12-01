/*
 * Copyright 2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.bremersee.geojson.example;

import org.bremersee.geojson.GeoJsonObjectMapperModule;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

/**
 * Add the GeoJSON object mapper model.
 *
 * @author Christian Bremer
 */
@Configuration
public class JacksonConfiguration implements Jackson2ObjectMapperBuilderCustomizer {

  @Override
  public void customize(Jackson2ObjectMapperBuilder jacksonObjectMapperBuilder) {
    jacksonObjectMapperBuilder.modules(new GeoJsonObjectMapperModule());
  }
}
