package com.jissuetracker.webapp.services;

import com.jissuetracker.webapp.models.Attachments;

/**
 * Created by jovin on 10/8/16.
 */
public interface AttachmentService {

    void add(Attachments attachments)throws Exception;
    void delete(Integer attachmentId)throws Exception;
    Attachments getByIdWithIssuesAndIssueUpdates(Integer attachmentId)throws Exception;
}
