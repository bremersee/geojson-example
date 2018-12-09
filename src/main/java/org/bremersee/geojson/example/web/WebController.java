/*
 * Copyright 2015-2018 the original author or authors.
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

package org.bremersee.geojson.example.web;

import java.util.LinkedHashMap;
import java.util.Map;
import org.bremersee.geojson.GeoJsonFeature;
import org.bremersee.geojson.GeoJsonFeatureCollection;
import org.bremersee.geojson.GeoJsonNamedCrs;
import org.bremersee.geojson.example.service.GeometryService;
import org.bremersee.geojson.utils.GeometryUtils;
import org.locationtech.jts.geom.LinearRing;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.Polygon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Christian Bremer
 */
@Controller
public class WebController {

  private final GeometryService geometryService;

  @Autowired
  public WebController(GeometryService geometryService) {
    this.geometryService = geometryService;
  }

  @RequestMapping(
      value = {"/main.html"},
      method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
  public String displayMainPage() {
    return "main";
  }

  @RequestMapping(
      value = "/static-features.json",
      method = RequestMethod.GET,
      produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  public GeoJsonFeatureCollection getStaticFeatures() {

    LinearRing linearRing = geometryService.getPositionsRing();
    Map<String, Object> linearRingProperties = new LinkedHashMap<>();
    linearRingProperties.put("color", "blue");
    linearRingProperties.put("width", 2);
    GeoJsonFeature positionsRingFeature = new GeoJsonFeature(
        null,
        GeometryUtils.transformWgs84ToMercator(linearRing),
        false,
        linearRingProperties);

    Polygon poly = geometryService.getPositionsBoundingBox();
    Map<String, Object> polyProperties = new LinkedHashMap<>();
    polyProperties.put("color", "red");
    polyProperties.put("width", 4);
    GeoJsonFeature positionsBoundingBoxFeature = new GeoJsonFeature(
        null,
        GeometryUtils.transformWgs84ToMercator(poly),
        false,
        polyProperties);

    GeoJsonFeatureCollection col = new GeoJsonFeatureCollection();
    col.getFeatures().add(positionsRingFeature);
    col.getFeatures().add(positionsBoundingBoxFeature);
    col.unknown("crs", new GeoJsonNamedCrs(GeometryUtils.MERCATOR_CRS));
    return col;
  }

  @RequestMapping(
      value = "/current-position.json",
      method = RequestMethod.GET,
      produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  public GeoJsonFeature getCurrentPosition() {

    Point position = geometryService.getNextPosition();
    return new GeoJsonFeature(
        null,
        GeometryUtils.transformWgs84ToMercator(position),
        false,
        null);
  }

}
