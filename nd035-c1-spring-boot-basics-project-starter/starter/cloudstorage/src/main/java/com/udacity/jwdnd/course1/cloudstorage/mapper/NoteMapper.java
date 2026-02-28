package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.entity.Note;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NoteMapper {

    @Insert("INSERT INTO NOTES (notetitle, notedescription, userid) VALUES (#{noteTitle}, #{noteDescription}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "noteId")
    int insert(Note note);

    @Update("UPDATE NOTES SET notetitle = #{noteTitle}, notedescription = #{noteDescription} WHERE noteid = #{noteId}")
    int update(Note note);

    @Select("SELECT * FROM NOTES WHERE userid = #{userId}")
    @Results({
            @Result(property = "noteId", column = "noteid"),
            @Result(property = "noteTitle", column = "notetitle"),
            @Result(property = "noteDescription", column = "notedescription"),
            @Result(property = "userId", column = "userid")
    })
    List<Note> getNotesByUserId(Integer userId);

    @Delete("DELETE FROM NOTES WHERE noteid = #{noteId}")
    void delete(Integer noteId);
}
