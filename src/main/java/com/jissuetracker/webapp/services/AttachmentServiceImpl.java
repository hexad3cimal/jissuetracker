package com.jissuetracker.webapp.services;

import com.jissuetracker.webapp.dao.AttachmentDao;
import com.jissuetracker.webapp.models.Attachments;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by jovin on 10/8/16.
 */

@Service("AttachmentService")
public class AttachmentServiceImpl implements AttachmentService {

    @Autowired
    AttachmentDao attachmentDao;

    @Transactional
    public void add(Attachments attachments) throws Exception {
        attachmentDao.add(attachments);
    }

    @Transactional
    public void delete(Integer attachmentId) throws Exception {

        attachmentDao.delete(attachmentId);
    }

   @Transactional(readOnly = true)
    public Attachments getByIdWithIssuesAndIssueUpdates(Integer attachmentId) throws Exception {
        return attachmentDao.getByIdWithIssuesAndIssueUpdates(attachmentId);
    }
}
