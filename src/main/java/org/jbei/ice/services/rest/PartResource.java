package org.jbei.ice.services.rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.jbei.ice.lib.access.PermissionException;
import org.jbei.ice.lib.access.PermissionsController;
import org.jbei.ice.lib.account.SessionHandler;
import org.jbei.ice.lib.common.logging.Logger;
import org.jbei.ice.lib.dto.ConfigurationKey;
import org.jbei.ice.lib.dto.History;
import org.jbei.ice.lib.dto.comment.UserComment;
import org.jbei.ice.lib.dto.entry.*;
import org.jbei.ice.lib.dto.permission.AccessPermission;
import org.jbei.ice.lib.dto.sample.PartSample;
<<<<<<< HEAD
=======
import org.jbei.ice.lib.entry.Entries;
>>>>>>> 3a93b296cacb68f217094cf7df86236a73cd323c
import org.jbei.ice.lib.entry.EntryController;
import org.jbei.ice.lib.entry.EntryCreator;
import org.jbei.ice.lib.entry.EntryRetriever;
import org.jbei.ice.lib.entry.attachment.AttachmentController;
import org.jbei.ice.lib.entry.sample.SampleController;
import org.jbei.ice.lib.entry.sequence.SequenceController;
import org.jbei.ice.lib.experiment.ExperimentController;
import org.jbei.ice.lib.experiment.Study;
<<<<<<< HEAD
=======
import org.jbei.ice.lib.net.TransferredParts;
>>>>>>> 3a93b296cacb68f217094cf7df86236a73cd323c
import org.jbei.ice.lib.utils.Utils;
import org.jbei.ice.lib.vo.FeaturedDNASequence;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.nio.file.Paths;
import java.util.ArrayList;
<<<<<<< HEAD
=======
import java.util.List;
>>>>>>> 3a93b296cacb68f217094cf7df86236a73cd323c
import java.util.Set;

/**
 * @author Hector Plahar
 */
@Path("/parts")
public class PartResource extends RestResource {

    private EntryController controller = new EntryController();
    private EntryRetriever retriever = new EntryRetriever();
    private PermissionsController permissionsController = new PermissionsController();
    private AttachmentController attachmentController = new AttachmentController();
    private SequenceController sequenceController = new SequenceController();
    private ExperimentController experimentController = new ExperimentController();
    private SampleController sampleController = new SampleController();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/autocomplete")
    public ArrayList<String> autoComplete(@QueryParam("val") String val,
<<<<<<< HEAD
            @DefaultValue("SELECTION_MARKERS") @QueryParam("field") String field,
            @DefaultValue("8") @QueryParam("limit") int limit) {
=======
                                          @DefaultValue("SELECTION_MARKERS") @QueryParam("field") String field,
                                          @DefaultValue("8") @QueryParam("limit") int limit) {
>>>>>>> 3a93b296cacb68f217094cf7df86236a73cd323c
        AutoCompleteField autoCompleteField = AutoCompleteField.valueOf(field.toUpperCase());
        Set<String> result = retriever.getMatchingAutoCompleteField(autoCompleteField, val, limit);
        return new ArrayList<>(result);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/autocomplete/partid")
    public ArrayList<PartData> autoComplete(@QueryParam("token") String token,
<<<<<<< HEAD
            @DefaultValue("8") @QueryParam("limit") int limit) {
=======
                                            @DefaultValue("8") @QueryParam("limit") int limit) {
>>>>>>> 3a93b296cacb68f217094cf7df86236a73cd323c
        return retriever.getMatchingPartNumber(token, limit);
    }

    /**
     * Retrieves a part using any of the unique identifiers. e.g. Part number, synthetic id, or global unique
     * identifier
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public Response read(@Context UriInfo info,
<<<<<<< HEAD
            @PathParam("id") String id,
            @HeaderParam(value = "X-ICE-Authentication-SessionId") String sessionId) {
=======
                         @PathParam("id") String id,
                         @HeaderParam(value = "X-ICE-Authentication-SessionId") String sessionId) {
>>>>>>> 3a93b296cacb68f217094cf7df86236a73cd323c
        String userId = SessionHandler.getUserIdBySession(sessionId);
        try {
            log(userId, "retrieving details for " + id);
            EntryType type = EntryType.nameToType(id);
            PartData data;
            if (type != null)
                data = controller.getPartDefaults(userId, type);
            else
                data = controller.retrieveEntryDetails(userId, id);
            return super.respond(data);
        } catch (PermissionException pe) {
            // todo : have a generic error entity returned
            return Response.status(Response.Status.FORBIDDEN).build();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}/tooltip")
    public PartData getTooltipDetails(@PathParam("id") String id,
<<<<<<< HEAD
            @HeaderParam(value = "X-ICE-Authentication-SessionId") String userAgentHeader) {
=======
                                      @HeaderParam(value = "X-ICE-Authentication-SessionId") String userAgentHeader) {
>>>>>>> 3a93b296cacb68f217094cf7df86236a73cd323c
        String userId = SessionHandler.getUserIdBySession(userAgentHeader);
        return controller.retrieveEntryTipDetails(userId, id);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}/permissions")
    public ArrayList<AccessPermission> getPermissions(@Context UriInfo info, @PathParam("id") String id,
<<<<<<< HEAD
            @HeaderParam(value = "X-ICE-Authentication-SessionId") String userAgentHeader) {
=======
                                                      @HeaderParam(value = "X-ICE-Authentication-SessionId") String userAgentHeader) {
>>>>>>> 3a93b296cacb68f217094cf7df86236a73cd323c
        String userId = getUserIdFromSessionHeader(userAgentHeader);
        return retriever.getEntryPermissions(userId, id);
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}/permissions")
    public PartData setPermissions(@Context UriInfo info, @PathParam("id") long partId,
<<<<<<< HEAD
            ArrayList<AccessPermission> permissions,
            @HeaderParam(value = "X-ICE-Authentication-SessionId") String userAgentHeader) {
=======
                                   ArrayList<AccessPermission> permissions,
                                   @HeaderParam(value = "X-ICE-Authentication-SessionId") String userAgentHeader) {
>>>>>>> 3a93b296cacb68f217094cf7df86236a73cd323c
        String userId = getUserIdFromSessionHeader(userAgentHeader);
        return permissionsController.setEntryPermissions(userId, partId, permissions);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}/experiments")
    public Response getPartExperiments(@PathParam("id") long partId,
<<<<<<< HEAD
            @HeaderParam(value = "X-ICE-Authentication-SessionId") String userAgentHeader) {
=======
                                       @HeaderParam(value = "X-ICE-Authentication-SessionId") String userAgentHeader) {
>>>>>>> 3a93b296cacb68f217094cf7df86236a73cd323c
        String userId = getUserIdFromSessionHeader(userAgentHeader);
        ArrayList<Study> studies = experimentController.getPartStudies(userId, partId);
        if (studies == null)
            return respond(Response.Status.INTERNAL_SERVER_ERROR);
        return respond(Response.Status.OK, studies);
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}/experiments")
    public Response getPartExperiments(@PathParam("id") long partId,
<<<<<<< HEAD
            @HeaderParam(value = "X-ICE-Authentication-SessionId") String userAgentHeader,
            Study study) {
=======
                                       @HeaderParam(value = "X-ICE-Authentication-SessionId") String userAgentHeader,
                                       Study study) {
>>>>>>> 3a93b296cacb68f217094cf7df86236a73cd323c
        String userId = getUserIdFromSessionHeader(userAgentHeader);
        study = experimentController.createStudy(userId, partId, study);
        return respond(Response.Status.OK, study);
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}/permissions/public")
    public Response enablePublicAccess(@Context UriInfo info, @PathParam("id") long partId,
<<<<<<< HEAD
            @HeaderParam(value = "X-ICE-Authentication-SessionId") String userAgentHeader) {
=======
                                       @HeaderParam(value = "X-ICE-Authentication-SessionId") String userAgentHeader) {
>>>>>>> 3a93b296cacb68f217094cf7df86236a73cd323c
        String userId = getUserIdFromSessionHeader(userAgentHeader);
        if (permissionsController.enablePublicReadAccess(userId, partId))
            return respond(Response.Status.OK);
        return respond(Response.Status.INTERNAL_SERVER_ERROR);
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}/permissions/public")
    public Response disablePublicAccess(@Context UriInfo info, @PathParam("id") long partId,
<<<<<<< HEAD
            @HeaderParam(value = "X-ICE-Authentication-SessionId") String userAgentHeader) {
=======
                                        @HeaderParam(value = "X-ICE-Authentication-SessionId") String userAgentHeader) {
>>>>>>> 3a93b296cacb68f217094cf7df86236a73cd323c
        String userId = getUserIdFromSessionHeader(userAgentHeader);
        if (permissionsController.disablePublicReadAccess(userId, partId))
            return respond(Response.Status.OK);
        return respond(Response.Status.INTERNAL_SERVER_ERROR);
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}/permissions")
    public AccessPermission createPermission(@Context UriInfo info, @PathParam("id") long partId,
<<<<<<< HEAD
            AccessPermission permission,
            @HeaderParam(value = "X-ICE-Authentication-SessionId") String userAgentHeader) {
=======
                                             AccessPermission permission,
                                             @HeaderParam(value = "X-ICE-Authentication-SessionId") String userAgentHeader) {
>>>>>>> 3a93b296cacb68f217094cf7df86236a73cd323c
        String userId = getUserIdFromSessionHeader(userAgentHeader);
        return permissionsController.createPermission(userId, partId, permission);
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}/permissions/{permissionId}")
    public Response removePermission(@Context UriInfo info,
<<<<<<< HEAD
            @PathParam("id") long partId,
            @PathParam("permissionId") long permissionId,
            @HeaderParam(value = "X-ICE-Authentication-SessionId") String userAgentHeader) {
=======
                                     @PathParam("id") long partId,
                                     @PathParam("permissionId") long permissionId,
                                     @HeaderParam(value = "X-ICE-Authentication-SessionId") String userAgentHeader) {
>>>>>>> 3a93b296cacb68f217094cf7df86236a73cd323c
        String userId = getUserIdFromSessionHeader(userAgentHeader);
        permissionsController.removeEntryPermission(userId, partId, permissionId);
        return Response.ok().build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}/statistics")
    public PartStatistics getStatistics(@PathParam("id") long partId,
<<<<<<< HEAD
            @HeaderParam(value = "X-ICE-Authentication-SessionId") String userAgentHeader) {
=======
                                        @HeaderParam(value = "X-ICE-Authentication-SessionId") String userAgentHeader) {
>>>>>>> 3a93b296cacb68f217094cf7df86236a73cd323c
        String userId = SessionHandler.getUserIdBySession(userAgentHeader);
        return controller.retrieveEntryStatistics(userId, partId);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}/comments")
    public ArrayList<UserComment> getComments(@Context UriInfo info, @PathParam("id") long partId,
<<<<<<< HEAD
            @HeaderParam(value = "X-ICE-Authentication-SessionId") String userAgentHeader) {
=======
                                              @HeaderParam(value = "X-ICE-Authentication-SessionId") String userAgentHeader) {
>>>>>>> 3a93b296cacb68f217094cf7df86236a73cd323c
        String userId = SessionHandler.getUserIdBySession(userAgentHeader);
        return controller.retrieveEntryComments(userId, partId);
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}/comments")
    public Response createComment(@PathParam("id") long partId,
<<<<<<< HEAD
            @HeaderParam(value = "X-ICE-Authentication-SessionId") String userAgentHeader,
            UserComment userComment) {
=======
                                  @HeaderParam(value = "X-ICE-Authentication-SessionId") String userAgentHeader,
                                  UserComment userComment) {
>>>>>>> 3a93b296cacb68f217094cf7df86236a73cd323c
//        if(userComment == null || userComment.getMessage() == null)
//            throw new Web
        // todo : check for null
        String userId = getUserIdFromSessionHeader(userAgentHeader);
        log(userId, "adding comment to entry " + partId);
        UserComment comment = controller.createEntryComment(userId, partId, userComment);
        return respond(comment);
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}/comments/{commentId}")
    public UserComment updateComment(@PathParam("id") long partId,
<<<<<<< HEAD
            @PathParam("commentId") long commentId,
            @HeaderParam(value = "X-ICE-Authentication-SessionId") String userAgentHeader,
            UserComment userComment) {
=======
                                     @PathParam("commentId") long commentId,
                                     @HeaderParam(value = "X-ICE-Authentication-SessionId") String userAgentHeader,
                                     UserComment userComment) {
>>>>>>> 3a93b296cacb68f217094cf7df86236a73cd323c
        String userId = getUserIdFromSessionHeader(userAgentHeader);
        return controller.updateEntryComment(userId, partId, commentId, userComment);
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/{id}/attachments")
    public AttachmentInfo addAttachment(@PathParam("id") long partId,
<<<<<<< HEAD
            @HeaderParam(value = "X-ICE-Authentication-SessionId") String userAgentHeader,
            AttachmentInfo attachment) {
=======
                                        @HeaderParam(value = "X-ICE-Authentication-SessionId") String userAgentHeader,
                                        AttachmentInfo attachment) {
>>>>>>> 3a93b296cacb68f217094cf7df86236a73cd323c
        // todo : check for null
        String userId = getUserIdFromSessionHeader(userAgentHeader);
        AttachmentController attachmentController = new AttachmentController();
        return attachmentController.addAttachmentToEntry(userId, partId, attachment);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}/attachments")
    public ArrayList<AttachmentInfo> getAttachments(@PathParam("id") long partId,
<<<<<<< HEAD
            @HeaderParam(value = "X-ICE-Authentication-SessionId") String userAgentHeader) {
=======
                                                    @HeaderParam(value = "X-ICE-Authentication-SessionId") String userAgentHeader) {
>>>>>>> 3a93b296cacb68f217094cf7df86236a73cd323c
        String userId = SessionHandler.getUserIdBySession(userAgentHeader);
        return attachmentController.getByEntry(userId, partId);
    }

    @DELETE
    @Path("/{id}/attachments/{attachmentId}")
    public Response deleteAttachment(@Context UriInfo info,
<<<<<<< HEAD
            @PathParam("id") long partId,
            @PathParam("attachmentId") long attachmentId,
            @HeaderParam(value = "X-ICE-Authentication-SessionId") String userAgentHeader) {
=======
                                     @PathParam("id") long partId,
                                     @PathParam("attachmentId") long attachmentId,
                                     @HeaderParam(value = "X-ICE-Authentication-SessionId") String userAgentHeader) {
>>>>>>> 3a93b296cacb68f217094cf7df86236a73cd323c
        String userId = getUserIdFromSessionHeader(userAgentHeader);
        if (!attachmentController.delete(userId, partId, attachmentId))
            return Response.notModified().build();    // todo : use 404 ?
        return Response.ok().build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}/history")
    public ArrayList<History> getHistory(@Context UriInfo info,
<<<<<<< HEAD
            @PathParam("id") long partId,
            @QueryParam("sid") String sessionId,
            @HeaderParam(value = "X-ICE-Authentication-SessionId") String userAgentHeader) {
=======
                                         @PathParam("id") long partId,
                                         @QueryParam("sid") String sessionId,
                                         @HeaderParam(value = "X-ICE-Authentication-SessionId") String userAgentHeader) {
>>>>>>> 3a93b296cacb68f217094cf7df86236a73cd323c
        if (StringUtils.isEmpty(userAgentHeader))
            userAgentHeader = sessionId;
        String userId = getUserIdFromSessionHeader(userAgentHeader);
        return controller.getHistory(userId, partId);
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}/history/{historyId}")
    public Response delete(@PathParam("id") long partId,
<<<<<<< HEAD
            @PathParam("historyId") long historyId,
            @HeaderParam(value = "X-ICE-Authentication-SessionId") String userAgentHeader) {
=======
                           @PathParam("historyId") long historyId,
                           @HeaderParam(value = "X-ICE-Authentication-SessionId") String userAgentHeader) {
>>>>>>> 3a93b296cacb68f217094cf7df86236a73cd323c
        String userId = getUserIdFromSessionHeader(userAgentHeader);
        boolean success = controller.deleteHistory(userId, partId, historyId);
        return super.respond(success);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}/traces")
    public ArrayList<TraceSequenceAnalysis> getTraces(@Context UriInfo info,
<<<<<<< HEAD
            @PathParam("id") long partId,
            @QueryParam("sid") String sessionId,
            @HeaderParam(value = "X-ICE-Authentication-SessionId") String userAgentHeader) {
=======
                                                      @PathParam("id") long partId,
                                                      @QueryParam("sid") String sessionId,
                                                      @HeaderParam(value = "X-ICE-Authentication-SessionId") String userAgentHeader) {
>>>>>>> 3a93b296cacb68f217094cf7df86236a73cd323c
        if (StringUtils.isEmpty(userAgentHeader))
            userAgentHeader = sessionId;
        String userId = SessionHandler.getUserIdBySession(userAgentHeader);
        return controller.getTraceSequences(userId, partId);
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}/traces")
    public Response addTraceSequence(@PathParam("id") long partId,
<<<<<<< HEAD
            @FormDataParam("file") InputStream fileInputStream,
            @FormDataParam("file") FormDataContentDisposition contentDispositionHeader,
            @QueryParam("sid") String sessionId,
            @HeaderParam(value = "X-ICE-Authentication-SessionId") String userAgentHeader) {
=======
                                     @FormDataParam("file") InputStream fileInputStream,
                                     @FormDataParam("file") FormDataContentDisposition contentDispositionHeader,
                                     @QueryParam("sid") String sessionId,
                                     @HeaderParam(value = "X-ICE-Authentication-SessionId") String userAgentHeader) {
>>>>>>> 3a93b296cacb68f217094cf7df86236a73cd323c
        if (StringUtils.isEmpty(userAgentHeader))
            userAgentHeader = sessionId;
        String userId = getUserIdFromSessionHeader(userAgentHeader);
        String fileName = contentDispositionHeader.getFileName();
        String tmpDir = Utils.getConfigValue(ConfigurationKey.TEMPORARY_DIRECTORY);
        File file = Paths.get(tmpDir, fileName).toFile();
        try {
            FileUtils.copyInputStreamToFile(fileInputStream, file);
        } catch (IOException e) {
            Logger.error(e);
            return respond(Response.Status.INTERNAL_SERVER_ERROR);
        }
        boolean success = controller.addTraceSequence(userId, partId, file, fileName);
        return respond(success);
    }

    @DELETE
    @Path("/{id}/traces/{traceId}")
    public Response deleteTrace(@Context UriInfo info, @PathParam("id") long partId,
<<<<<<< HEAD
            @PathParam("traceId") long traceId,
            @HeaderParam(value = "X-ICE-Authentication-SessionId") String userAgentHeader) {
=======
                                @PathParam("traceId") long traceId,
                                @HeaderParam(value = "X-ICE-Authentication-SessionId") String userAgentHeader) {
>>>>>>> 3a93b296cacb68f217094cf7df86236a73cd323c
        String userId = getUserIdFromSessionHeader(userAgentHeader);
        if (!controller.deleteTraceSequence(userId, partId, traceId))
            return super.respond(Response.Status.UNAUTHORIZED);
        return super.respond(Response.Status.OK);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}/samples")
    public ArrayList<PartSample> getSamples(@Context UriInfo info, @PathParam("id") long partId,
<<<<<<< HEAD
            @HeaderParam(value = "X-ICE-Authentication-SessionId") String userAgentHeader) {
=======
                                            @HeaderParam(value = "X-ICE-Authentication-SessionId") String userAgentHeader) {
>>>>>>> 3a93b296cacb68f217094cf7df86236a73cd323c
        String userId = SessionHandler.getUserIdBySession(userAgentHeader);
        return sampleController.retrieveEntrySamples(userId, partId);
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}/samples")
    public ArrayList<PartSample> addSample(@Context UriInfo info, @PathParam("id") long partId,
                                           @QueryParam("strainNamePrefix") String strainNamePrefix,
<<<<<<< HEAD
            @HeaderParam(value = "X-ICE-Authentication-SessionId") String userAgentHeader,
            PartSample partSample) {
=======
                                           @HeaderParam(value = "X-ICE-Authentication-SessionId") String userAgentHeader,
                                           PartSample partSample) {
>>>>>>> 3a93b296cacb68f217094cf7df86236a73cd323c
        String userId = SessionHandler.getUserIdBySession(userAgentHeader);
        log(userId, "creating sample for part " + partId);
        sampleController.createSample(userId, partId, partSample, strainNamePrefix);
        return sampleController.retrieveEntrySamples(userId, partId);
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}/samples/{sampleId}")
    public Response deleteSample(@Context UriInfo info, @PathParam("id") long partId,
<<<<<<< HEAD
            @HeaderParam(value = "X-ICE-Authentication-SessionId") String userAgentHeader,
            @PathParam("sampleId") long sampleId) {
=======
                                 @HeaderParam(value = "X-ICE-Authentication-SessionId") String userAgentHeader,
                                 @PathParam("sampleId") long sampleId) {
>>>>>>> 3a93b296cacb68f217094cf7df86236a73cd323c
        String userId = SessionHandler.getUserIdBySession(userAgentHeader);
        boolean success = sampleController.delete(userId, partId, sampleId);
        return super.respond(success);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}/sequence")
    public Response getSequence(@PathParam("id") long partId,
<<<<<<< HEAD
            @QueryParam("sid") String sessionId,
            @HeaderParam(value = "X-ICE-Authentication-SessionId") String userAgentHeader) {
=======
                                @QueryParam("sid") String sessionId,
                                @HeaderParam(value = "X-ICE-Authentication-SessionId") String userAgentHeader) {
>>>>>>> 3a93b296cacb68f217094cf7df86236a73cd323c
        if (StringUtils.isEmpty(userAgentHeader))
            userAgentHeader = sessionId;

        String userId = SessionHandler.getUserIdBySession(userAgentHeader);
        FeaturedDNASequence sequence = sequenceController.retrievePartSequence(userId, partId);
        if (sequence == null)
            return Response.status(Response.Status.NO_CONTENT).build();
        return Response.status(Response.Status.OK).entity(sequence).build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}/sequence")
    public FeaturedDNASequence updateSequence(@PathParam("id") long partId,
<<<<<<< HEAD
            @QueryParam("sid") String sessionId,
            FeaturedDNASequence sequence,
            @HeaderParam(value = "X-ICE-Authentication-SessionId") String userAgentHeader) {
=======
                                              @QueryParam("sid") String sessionId,
                                              FeaturedDNASequence sequence,
                                              @HeaderParam(value = "X-ICE-Authentication-SessionId") String userAgentHeader) {
>>>>>>> 3a93b296cacb68f217094cf7df86236a73cd323c
        if (StringUtils.isEmpty(userAgentHeader))
            userAgentHeader = sessionId;

        String userId = getUserIdFromSessionHeader(userAgentHeader);
        return sequenceController.updateSequence(userId, partId, sequence);
    }

    @DELETE
    @Path("/{id}/sequence")
    public Response deleteSequence(@PathParam("id") long partId,
<<<<<<< HEAD
            @QueryParam("sid") String sessionId,
            @HeaderParam(value = "X-ICE-Authentication-SessionId") String userAgentHeader) {
=======
                                   @QueryParam("sid") String sessionId,
                                   @HeaderParam(value = "X-ICE-Authentication-SessionId") String userAgentHeader) {
>>>>>>> 3a93b296cacb68f217094cf7df86236a73cd323c
        String userId = getUserIdFromSessionHeader(userAgentHeader);
        try {
            if (sequenceController.deleteSequence(userId, partId))
                return Response.ok().build();
            return Response.serverError().build();
        } catch (RuntimeException e) {
            Logger.error(e);
            return Response.serverError().build();
        }
    }

<<<<<<< HEAD
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public PartData create(@Context UriInfo info,
            @HeaderParam(value = "X-ICE-Authentication-SessionId") String userAgentHeader,
            PartData partData) {
=======
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public PartData create(@Context UriInfo info,
                           @HeaderParam(value = "X-ICE-Authentication-SessionId") String userAgentHeader,
                           PartData partData) {
>>>>>>> 3a93b296cacb68f217094cf7df86236a73cd323c
        String userId = getUserIdFromSessionHeader(userAgentHeader);
        EntryCreator creator = new EntryCreator();
        long id = creator.createPart(userId, partData);
        log(userId, "created entry " + id);
        partData.setId(id);
        return partData;
    }

    @PUT
    @Path("/transfer")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response transfer(PartData partData) {
<<<<<<< HEAD
        EntryCreator creator = new EntryCreator();
        PartData response = creator.receiveTransferredEntry(partData);
        return super.respond(Response.Status.OK, response);
=======
        TransferredParts transferredParts = new TransferredParts();
        PartData response = transferredParts.receiveTransferredEntry(partData);
        return super.respond(response);
>>>>>>> 3a93b296cacb68f217094cf7df86236a73cd323c
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public PartData update(@Context UriInfo info,
<<<<<<< HEAD
            @PathParam("id") long partId,
            @HeaderParam(value = "X-ICE-Authentication-SessionId") String userAgentHeader,
            PartData partData) {
=======
                           @PathParam("id") long partId,
                           @HeaderParam(value = "X-ICE-Authentication-SessionId") String userAgentHeader,
                           PartData partData) {
>>>>>>> 3a93b296cacb68f217094cf7df86236a73cd323c
        String userId = getUserIdFromSessionHeader(userAgentHeader);
        long id = controller.updatePart(userId, partId, partData);
        log(userId, "updated entry " + id);
        partData.setId(id);
        return partData;
    }

    @DELETE
    @Path("/{id}")
    public void delete(@PathParam("id") long id) {
        Logger.info("Deleting part " + id);
    }

    @POST
    @Path("/trash")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response moveToTrash(ArrayList<PartData> list,
<<<<<<< HEAD
            @HeaderParam(value = "X-ICE-Authentication-SessionId") String userAgentHeader) {
=======
                                @HeaderParam(value = "X-ICE-Authentication-SessionId") String userAgentHeader) {
>>>>>>> 3a93b296cacb68f217094cf7df86236a73cd323c
        String userId = getUserIdFromSessionHeader(userAgentHeader);
        Type fooType = new TypeToken<ArrayList<PartData>>() {
        }.getType();
        Gson gson = new GsonBuilder().create();
        ArrayList<PartData> data = gson.fromJson(gson.toJsonTree(list), fooType);
        boolean success = controller.moveEntriesToTrash(userId, data);
        return respond(success);
    }

    /**
     * Removes the linkId from id
     *
     * @param partId     id of entry whose link we are removing
     * @param linkedPart
     * @param sessionId
     * @return
     */
    @DELETE
    @Path("/{id}/links/{linkedId}")
    public Response deleteLink(@PathParam("id") long partId,
<<<<<<< HEAD
            @PathParam("linkedId") long linkedPart,
            @HeaderParam(value = "X-ICE-Authentication-SessionId") String sessionId) {
=======
                               @PathParam("linkedId") long linkedPart,
                               @HeaderParam(value = "X-ICE-Authentication-SessionId") String sessionId) {
>>>>>>> 3a93b296cacb68f217094cf7df86236a73cd323c
        String userId = getUserIdFromSessionHeader(sessionId);
        log(userId, "removing link " + linkedPart + " from " + partId);
        boolean success = controller.removeLink(userId, partId, linkedPart);
        return respond(success);
    }
<<<<<<< HEAD
=======

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateEntries(@HeaderParam(value = "X-ICE-Authentication-SessionId") String sessionId,
                                  @QueryParam(value = "visibility") Visibility visibility,
                                  List<Long> entryIds) {
        String userId = getUserIdFromSessionHeader(sessionId);
        log(userId, "updating visibility of " + entryIds.size() + " entries to " + visibility);
        Entries entries = new Entries();
        List<Long> arrayList = new ArrayList<>();
        for (Number id : entryIds)
            arrayList.add(id.longValue());
        boolean success = entries.updateVisibility(userId, arrayList, visibility);
        return super.respond(success);
    }
>>>>>>> 3a93b296cacb68f217094cf7df86236a73cd323c
}
