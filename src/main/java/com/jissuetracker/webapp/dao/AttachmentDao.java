package com.jissuetracker.webapp.dao;

import com.jissuetracker.webapp.models.Attachments;

/**
 * Created by jovin on 10/8/16.
 */
public interface AttachmentDao {

    void add(Attachments attachments)throws Exception;
    void delete(Integer attachmentId)throws Exception;
    Attachments getByIdWithIssuesAndIssueUpdates(Integer attachmentId)throws Exception;

}
