package com.D6.configclient;

import com.D6.configclient.countries.CountryService;
import com.D6.configclient.countries.dtos.CountryDTO;
import com.D6.configclient.countries.soap.*;
import com.D6.configclient.movies.dtos.MoviesResultDTO;
import com.D6.configclient.music.dtos.Artist;
import com.D6.configclient.movies.MoviesService;
import com.D6.configclient.commons.DBCPDataSource;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.junit.jupiter.api.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.TestPropertySource;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.sql.SQLException;
import java.time.Year;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = ConfigClientApplication.class)
@TestPropertySource(locations = "classpath:application.yaml")
public class ConfigClientApplicationTest {

    @Autowired
    private CountryService countryService;

    @Autowired
    private MoviesService moviesService;

    @Autowired
    private DBCPDataSource dbcpDataSource;

    @Test
    @Order(1)
    void contextLoads() {
    }

    @Test
    @Order(2)
    void dbConnection() throws SQLException {
        assertThat(dbcpDataSource.getConnection().isValid(500)).isTrue();
    }

    @Test
    @Order(3)
    void singletonDBCP() throws SQLException {
        DBCPDataSource dbcpDataSource_B = new DBCPDataSource();
        assertThat(dbcpDataSource.getDataSource()).isEqualTo(dbcpDataSource_B.getDataSource());
    }

    @Test
    @Order(4)
    void artistsIsNotEmpty() throws SQLException {
        BeanListHandler<Artist> handler = new BeanListHandler<>(Artist.class);
        QueryRunner queryRunner = new QueryRunner();

        List<Artist> artistList = queryRunner.query(dbcpDataSource.getConnection(), "SELECT * FROM Artist LIMIT 1", handler);

        assertThat(artistList).isNotNull();
        assertThat(artistList).isNotEmpty();
    }

    @Test
    void getMoviesFromYear() {
        MoviesResultDTO strMovies = moviesService.getMoviesByYear(Year.of(2020));
        assertThat(strMovies).isNotNull();
    }

    @Test
    void getRawCountries() throws IOException, ParserConfigurationException, SAXException {

        Envelope envelope = countryService.getRawCountries();

        assertThat(envelope).isNotNull();
        assertThat(envelope.getBody()).isNotNull();
        assertThat(envelope.getBody().getResponse()).isNotNull();
        assertThat(envelope.getBody().getResponse().getResult()).isNotNull();
        assertThat(envelope.getBody().getResponse().getResult().getCountries()).isNotNull();
        assertThat(envelope.getBody().getResponse().getResult().getCountries()).isNotEmpty();
    }

    @Test
    void getCountries() throws IOException, ParserConfigurationException, SAXException {

        List<CountryDTO> countries = countryService.getCountries();

        assertThat(countries).isNotNull();
        assertThat(countries).isNotEmpty();
        assertThat(countries).hasSize(246);
    }
}
