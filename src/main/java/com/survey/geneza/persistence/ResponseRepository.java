package com.survey.geneza.persistence;  
import com.survey.geneza.domain.Response;

import java.math.BigInteger;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


@Repository
public interface ResponseRepository extends JpaRepository<Response, Long> {	 
    Response findById(Integer id);
    List<Response> findAll();
    public List<Response> findAllBySurveyId(Integer surveyId);
    public List<Response> findAllByParticipantId(Integer participantId);
    
    @Query("SELECT r FROM Response r JOIN ResponseAnswers ra ON r.id = ra.response WHERE r.survey = :surveyId AND ra.question = :questionId")
    List<Response> findAllBySurveyIdAndQuestionId(Integer surveyId, Integer questionId); 

    @Query("SELECT r FROM Response r WHERE r.participant.person.id = :personId")
List<Response> findAllByPersonId(Integer personId);

   Page<Response> findAll(Pageable pageable);
   List<Response> findByLinkTypeAndLinkId(String linkType, Integer linkId);
   List<Response> findByBatchIdAndLinkId(String batchId, Integer linkId);
    @Query("SELECT r FROM Response r " +
        "WHERE (:batchId IS NULL OR r.batchId = :batchId) " +
        "AND (:personId IS NULL OR r.participant.person.id = :personId) " +
        "AND (:linkId IS NULL OR r.linkId = :linkId) " +
        "AND (:role IS NULL OR r.role = :role) " +
        "AND (:surveyId IS NULL OR r.survey.id = :surveyId)")
    List<Response> searchResponses(
        @Param("batchId") Integer batchId,
        @Param("personId") Integer personId,
        @Param("linkId") Integer linkId,
        @Param("role") String role,
        @Param("surveyId") Integer surveyId
    );

//    List<Response> findByBatchIdAndPersonIdAndLinkIdAndSurveyId(String batchId, Integer personId, Integer linkId,
//         Integer surveyId);

// @Query(value = "SELECT e.batch " +
//                "FROM enrollment e " +
//                "JOIN participant p ON e.student = p.person " +
//                "WHERE p.id = :participantId " +
//                "LIMIT 1", nativeQuery = true)

//     Integer findBatchFromEnrollment(@Param("participantId") Integer participantId);

// @Query(value = "SELECT e.batch " +
//                "FROM enrollment e " +
//                "JOIN participant p ON e.student = p.person " +
//                "WHERE p.id = :participantId " +
//                "LIMIT 1", nativeQuery = true)

//     Integer findBatchFromMentor(@Param("participantId") Integer participantId);

//     @Query(value = "SELECT bm.batchid " +
//                "FROM batchmentor bm " +
//                "JOIN mentor m ON bm.mentorid = m.id " +
//                "JOIN participant p ON m.mentorid = p.person " +
//                "WHERE p.id = :participantId " +
//                "LIMIT 1", nativeQuery = true)
//     Integer findBatchFromTrainer(@Param("participantId") Integer participantId);


@Query(value = "SELECT b.id, b.batch FROM batch b WHERE b.id IN (" +
               "SELECT e.batch FROM enrollment e WHERE e.student = :personId " +
               "UNION " +
               "SELECT bm.batchid FROM batchmentor bm JOIN mentor m ON bm.mentorid = m.id WHERE m.mentorid = :personId " +
               "UNION " +
               "SELECT bt.batchid FROM batchtrainer bt JOIN trainer t ON bt.trainerid = t.id WHERE t.trainerid = :personId" +
               ")", nativeQuery = true)
List<Object[]> findAllBatchIdsLinkedToPerson(@Param("personId") Integer personId);

List<Response> findByParticipantAndLinkIdAndBatchId(Integer participant, Integer linkId, Integer batchId);


}