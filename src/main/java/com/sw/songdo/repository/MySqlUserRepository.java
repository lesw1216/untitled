package com.sw.songdo.repository;

import com.sw.songdo.dto.UserResDTO;
import com.sw.songdo.entity.UserEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.support.JdbcUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Slf4j
@Repository
public class MySqlUserRepository implements UserRepository {

    private final DataSource dataSource;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    MySqlUserRepository(DataSource dataSource, PasswordEncoder passwordEncoder) {
        this.dataSource = dataSource;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public UserEntity save(UserResDTO userResDTO) {
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(userResDTO.getUsername());
        userEntity.setPassword(userResDTO.getPassword());
        userEntity.setName(userResDTO.getName());
        userEntity.setSignUpDate(LocalDateTime.now());


        String sql = "insert into users(username, password, name, signup_date) values(?, ?, ?, ?)";

        Connection con = null;
        PreparedStatement pstmt = null;

        try {
            con = getConnection();
            pstmt = con.prepareStatement(sql);

            pstmt.setString(1, userEntity.getUsername());
            pstmt.setString(2, passwordEncoder.encode(userEntity.getPassword()));
            pstmt.setString(3, userEntity.getName());
            pstmt.setTimestamp(4, Timestamp.valueOf(userEntity.getSignUpDate()));
            pstmt.executeUpdate();
            return userEntity;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            close(con, pstmt, null);
        }
    }

    private void close(Connection con, PreparedStatement pstmt, ResultSet rs) {
        JdbcUtils.closeResultSet(rs);
        JdbcUtils.closeStatement(pstmt);
        JdbcUtils.closeConnection(con);
    }

    @Override
    public UserEntity findByUsername(String username) {
        String sql = "select * from users where username = ?";

        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            con = getConnection();
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, username);

            rs = pstmt.executeQuery();
            if (rs.next()) {
                UserEntity userEntity = new UserEntity();
                userEntity.setUsername(rs.getString("username"));
                userEntity.setPassword(rs.getString("password"));
                userEntity.setName(rs.getString("name"));
                userEntity.setSignUpDate(rs.getTimestamp("signup_date").toLocalDateTime());
                log.info(userEntity.toString());
                return userEntity;
            } else {
                return null;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            close(con, pstmt, rs);
        }
    }

    private Connection getConnection() throws SQLException {
        Connection con = dataSource.getConnection();
        log.info("conn={}, class of conn={}", con, con.getClass());
        return con;
    }
}
