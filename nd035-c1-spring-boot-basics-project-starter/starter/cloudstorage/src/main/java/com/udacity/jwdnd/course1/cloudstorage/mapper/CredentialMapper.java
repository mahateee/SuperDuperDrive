package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.entity.Credential;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CredentialMapper {

    @Select("SELECT * FROM CREDENTIALS WHERE userid = #{userId}")
    @Results({
            @Result(property = "credentialId", column = "credentialid"),
            @Result(property = "url", column = "url"),
            @Result(property = "username", column = "username"),
            @Result(property = "key", column = "key"),
            @Result(property = "password", column = "password"),
            @Result(property = "userId", column = "userid")
    })
    List<Credential> getCredentialsByUserId(Integer userId);

    @Insert("INSERT INTO CREDENTIALS (url, username, key, password, userid) VALUES (#{url}, #{username}, #{key}, #{password}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "credentialId")
    int insert(Credential credential);

    @Update("UPDATE CREDENTIALS SET url = #{url}, username = #{username}, " +
            "password = #{password} WHERE credentialid = #{credentialId}")
    int update(Credential credential);

    @Delete("DELETE FROM CREDENTIALS WHERE credentialid = #{credentialId}")
    int delete(Integer credentialId);
}
