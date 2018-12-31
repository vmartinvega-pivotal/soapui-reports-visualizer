/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package it.sdp.soapui.web;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class MoviesBean {

    public Movie find(Long id) {
        return new Movie();
    }

    public void addMovie(Movie movie) {
        
    }

    public void editMovie(Movie movie) {
        
    }

    public void deleteMovie(Movie movie) {
        
    }

    public void deleteMovieId(long id) {
    }

    public List<Movie> getMovies() {
        return new ArrayList<Movie>();
    }

    public List<Movie> findAll(int firstResult, int maxResults) {
    	return new ArrayList<Movie>();
    }

    public int countAll() {
        return 0;
    }

    public int count(String field, String searchTerm) {
       return 0;
    }

    public List<Movie> findRange(String field, String searchTerm, int firstResult, int maxResults) {
    	return new ArrayList<Movie>();
    }

    public void clean() {
    }
}