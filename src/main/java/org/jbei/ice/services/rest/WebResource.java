package org.jbei.ice.services.rest;

<<<<<<< HEAD
=======
import org.hsqldb.lib.StringUtil;
>>>>>>> 3a93b296cacb68f217094cf7df86236a73cd323c
import org.jbei.ice.lib.dto.entry.AttachmentInfo;
import org.jbei.ice.lib.dto.entry.PartData;
import org.jbei.ice.lib.dto.entry.PartStatistics;
import org.jbei.ice.lib.dto.web.RegistryPartner;
import org.jbei.ice.lib.dto.web.WebEntries;
<<<<<<< HEAD
import org.jbei.ice.lib.dto.web.WebOfRegistries;
import org.jbei.ice.lib.entry.EntrySelection;
import org.jbei.ice.lib.net.RemoteAccessController;
=======
import org.jbei.ice.lib.entry.EntrySelection;
import org.jbei.ice.lib.net.RemoteContact;
import org.jbei.ice.lib.net.RemoteEntries;
>>>>>>> 3a93b296cacb68f217094cf7df86236a73cd323c
import org.jbei.ice.lib.net.WoRController;
import org.jbei.ice.lib.vo.FeaturedDNASequence;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.ArrayList;

/**
<<<<<<< HEAD
=======
 * Resource for web of registries requests
 *
>>>>>>> 3a93b296cacb68f217094cf7df86236a73cd323c
 * @author Hector Plahar
 */
@Path("/web")
public class WebResource extends RestResource {

<<<<<<< HEAD
    private WoRController controller = new WoRController();
    private RemoteAccessController remoteAccessController = new RemoteAccessController();
=======
    private final WoRController controller = new WoRController();
    private final RemoteEntries remoteEntries = new RemoteEntries();
>>>>>>> 3a93b296cacb68f217094cf7df86236a73cd323c

    /**
     * Retrieves information on other ice instances that is in a web of registries
     * configuration with this instance; also know as registry partners
     *
<<<<<<< HEAD
     * @param approvedOnly    if true, only instances that have been approved are returned; defaults to true
     * @param userAgentHeader session if for user
=======
     * @param approvedOnly    if true (default), only instances that have been approved are returned
     * @param userAgentHeader session id for user logged in user
>>>>>>> 3a93b296cacb68f217094cf7df86236a73cd323c
     * @return wrapper around the list of registry partners
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
<<<<<<< HEAD
    public WebOfRegistries query(
            @DefaultValue("true") @QueryParam("approved_only") boolean approvedOnly,
            @HeaderParam(value = "X-ICE-Authentication-SessionId") String userAgentHeader) {
        String userId = getUserIdFromSessionHeader(userAgentHeader);
        return controller.getRegistryPartners(approvedOnly);
    }

    // get public entries
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}/entries")
    public Response getWebEntries(@Context UriInfo uriInfo,
=======
    public Response query(
            @DefaultValue("true") @QueryParam("approved_only") boolean approvedOnly,
            @HeaderParam(AUTHENTICATION_PARAM_NAME) String userAgentHeader) {
        getUserIdFromSessionHeader(userAgentHeader);
        return super.respond(controller.getRegistryPartners(approvedOnly));
    }

    /**
     * Retrieves entries for specified partner using the specified paging parameters
     *
     * @param partnerId unique identifier for registry partner whose entries are being retrieved
     * @param offset    record retrieve offset paging parameter
     * @param limit     maximum number of entries to retrieve
     * @param sort      field to sort on
     * @param asc       sort order
     * @param sessionId unique identifier for user making request
     * @return <code>OK</code> HTTP status with the list of entries wrapped in a result object
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}/entries")
    public Response getWebEntries(
>>>>>>> 3a93b296cacb68f217094cf7df86236a73cd323c
            @PathParam("id") long partnerId,
            @DefaultValue("0") @QueryParam("offset") int offset,
            @DefaultValue("15") @QueryParam("limit") int limit,
            @DefaultValue("created") @QueryParam("sort") String sort,
            @DefaultValue("false") @QueryParam("asc") boolean asc,
<<<<<<< HEAD
            @HeaderParam(value = "X-ICE-Authentication-SessionId") String sessionId) {
        WebEntries result = remoteAccessController.getPublicEntries(partnerId, offset, limit, sort, asc);
=======
            @HeaderParam(AUTHENTICATION_PARAM_NAME) String sessionId) {
        String userId = getUserIdFromSessionHeader(sessionId);
        WebEntries result = remoteEntries.getPublicEntries(userId, partnerId, offset, limit, sort, asc);
>>>>>>> 3a93b296cacb68f217094cf7df86236a73cd323c
        return super.respond(Response.Status.OK, result);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}/entries/{entryId}/attachments")
<<<<<<< HEAD
    public ArrayList<AttachmentInfo> getAttachments(@PathParam("id") long partnerId,
            @PathParam("entryId") long partId,
            @HeaderParam(value = "X-ICE-Authentication-SessionId") String userAgentHeader) {
//        String userId = getUserIdFromSessionHeader(userAgentHeader);
        return remoteAccessController.getPublicEntryAttachments(partnerId, partId);
=======
    public ArrayList<AttachmentInfo> getAttachments(
            @PathParam("id") long partnerId,
            @PathParam("entryId") long partId,
            @HeaderParam(AUTHENTICATION_PARAM_NAME) String userAgentHeader) {
        String userId = getUserIdFromSessionHeader(userAgentHeader);
        return remoteEntries.getEntryAttachments(userId, partnerId, partId);
>>>>>>> 3a93b296cacb68f217094cf7df86236a73cd323c
    }

    @POST
    @Path("/{id}/transfer")
    public Response transferEntries(
            @PathParam("id") long remoteId,
            EntrySelection entrySelection,
<<<<<<< HEAD
            @HeaderParam(value = "X-ICE-Authentication-SessionId") String sessionId) {
        String userId = super.getUserIdFromSessionHeader(sessionId);
        remoteAccessController.transferEntries(userId, remoteId, entrySelection);
=======
            @HeaderParam(AUTHENTICATION_PARAM_NAME) String sessionId) {
        String userId = super.getUserIdFromSessionHeader(sessionId);
        remoteEntries.transferEntries(userId, remoteId, entrySelection);
>>>>>>> 3a93b296cacb68f217094cf7df86236a73cd323c
        return super.respond(Response.Status.OK);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}/entries/{entryId}")
    public Response getWebEntry(@Context UriInfo uriInfo,
<<<<<<< HEAD
            @PathParam("id") long partnerId,
            @PathParam("entryId") long entryId,
            @HeaderParam(value = "X-ICE-Authentication-SessionId") String sessionId) {
        PartData result = remoteAccessController.getPublicEntry(partnerId, entryId);
=======
                                @PathParam("id") long partnerId,
                                @PathParam("entryId") long entryId,
                                @HeaderParam(AUTHENTICATION_PARAM_NAME) String sessionId) {
        String userId = super.getUserIdFromSessionHeader(sessionId);
        PartData result = remoteEntries.getPublicEntry(userId, partnerId, entryId);
>>>>>>> 3a93b296cacb68f217094cf7df86236a73cd323c
        return super.respond(Response.Status.OK, result);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}/entries/{entryId}/tooltip")
    public Response getWebEntryTooltip(@Context UriInfo uriInfo,
<<<<<<< HEAD
            @PathParam("id") long partnerId,
            @PathParam("entryId") long entryId,
            @HeaderParam(value = "X-ICE-Authentication-SessionId") String sessionId) {
        PartData result = remoteAccessController.getPublicEntryTooltip(partnerId, entryId);
=======
                                       @PathParam("id") long partnerId,
                                       @PathParam("entryId") long entryId,
                                       @HeaderParam(AUTHENTICATION_PARAM_NAME) String sessionId) {
        String userId = super.getUserIdFromSessionHeader(sessionId);
        PartData result = remoteEntries.getPublicEntryTooltip(userId, partnerId, entryId);
>>>>>>> 3a93b296cacb68f217094cf7df86236a73cd323c
        return super.respond(Response.Status.OK, result);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}/entries/{entryId}/statistics")
    public Response getStatistics(@PathParam("id") long partnerId,
<<<<<<< HEAD
            @PathParam("entryId") long entryId,
            @HeaderParam(value = "X-ICE-Authentication-SessionId") String userAgentHeader) {
        PartStatistics statistics = remoteAccessController.getPublicEntryStatistics(partnerId, entryId);
=======
                                  @PathParam("entryId") long entryId,
                                  @HeaderParam(AUTHENTICATION_PARAM_NAME) String sessionId) {
        String userId = super.getUserIdFromSessionHeader(sessionId);
        PartStatistics statistics = remoteEntries.getPublicEntryStatistics(userId, partnerId, entryId);
>>>>>>> 3a93b296cacb68f217094cf7df86236a73cd323c
        return super.respond(statistics);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}/entries/{entryId}/sequence")
    public Response getWebEntrySequence(@Context UriInfo uriInfo,
<<<<<<< HEAD
            @PathParam("id") long partnerId,
            @PathParam("entryId") long entryId,
            @HeaderParam(value = "X-ICE-Authentication-SessionId") String sessionId) {
        FeaturedDNASequence result = remoteAccessController.getPublicEntrySequence(partnerId, entryId);
=======
                                        @PathParam("id") long partnerId,
                                        @PathParam("entryId") long entryId,
                                        @HeaderParam(AUTHENTICATION_PARAM_NAME) String sessionId) {
        String userId = super.getUserIdFromSessionHeader(sessionId);
        FeaturedDNASequence result = remoteEntries.getPublicEntrySequence(userId, partnerId, entryId);
>>>>>>> 3a93b296cacb68f217094cf7df86236a73cd323c
        return super.respond(Response.Status.OK, result);
    }

    @POST
    @Path("/partner")
    // admin function
    public Response addWebPartner(@Context UriInfo info,
<<<<<<< HEAD
            @HeaderParam(value = "X-ICE-Authentication-SessionId") String sessionId,
            RegistryPartner partner) {
        String userId = getUserIdFromSessionHeader(sessionId);
        RegistryPartner registryPartner = controller.addWebPartner(userId, partner);
=======
                                  @HeaderParam(AUTHENTICATION_PARAM_NAME) String sessionId,
                                  RegistryPartner partner) {
        String userId = getUserIdFromSessionHeader(sessionId);
        RemoteContact contactRemote = new RemoteContact();
        RegistryPartner registryPartner = contactRemote.addWebPartner(userId, partner);
>>>>>>> 3a93b296cacb68f217094cf7df86236a73cd323c
        return respond(Response.Status.OK, registryPartner);
    }

    @GET
    @Path("/partner/{id}")
    public Response getWebPartner(@Context UriInfo info,
<<<<<<< HEAD
            @PathParam("id") long partnerId,
            @HeaderParam(value = "X-ICE-Authentication-SessionId") String sessionId) {
=======
                                  @PathParam("id") long partnerId,
                                  @HeaderParam(AUTHENTICATION_PARAM_NAME) String sessionId) {
>>>>>>> 3a93b296cacb68f217094cf7df86236a73cd323c
        String userId = getUserIdFromSessionHeader(sessionId);
        RegistryPartner partner = controller.getWebPartner(userId, partnerId);
        return super.respond(Response.Status.OK, partner);
    }

    @POST
    @Path("/partner/remote")
    public Response remoteWebPartnerRequest(RegistryPartner partner) {
<<<<<<< HEAD
        if (controller.addRemoteWebPartner(partner))
            return respond(Response.Status.OK);
        return respond(Response.Status.INTERNAL_SERVER_ERROR);
=======
        RemoteContact remoteContact = new RemoteContact();
        return super.respond(remoteContact.handleRemoteAddRequest(partner));
    }

    @DELETE
    @Path("/partner/remote")
    public Response remoteWebPartnerRemoveRequest(
            @HeaderParam(WOR_PARTNER_TOKEN) String worToken,
            @QueryParam("url") String url) {
        RemoteContact remoteContact = new RemoteContact();
        return super.respond(remoteContact.handleRemoteRemoveRequest(worToken, url));
    }

    @GET
    @Path("/partners")
    public Response getWebPartners(@HeaderParam(AUTHENTICATION_PARAM_NAME) String sessionId,
                                   @HeaderParam(WOR_PARTNER_TOKEN) String worToken,
                                   @QueryParam("url") String url) {
        if (StringUtil.isEmpty(sessionId))
            return super.respond(controller.getWebPartners(worToken, url));
        String userId = getUserIdFromSessionHeader(sessionId);
        return super.respond(controller.getWebPartners(userId));
>>>>>>> 3a93b296cacb68f217094cf7df86236a73cd323c
    }

    @PUT
    @Path("/partner/{url}")
    public Response updateWebPartner(
            @PathParam("url") String url, RegistryPartner partner,
<<<<<<< HEAD
            @HeaderParam(value = "X-ICE-Authentication-SessionId") String sessionId) {
=======
            @HeaderParam(AUTHENTICATION_PARAM_NAME) String sessionId) {
>>>>>>> 3a93b296cacb68f217094cf7df86236a73cd323c
        String userId = getUserIdFromSessionHeader(sessionId);
        if (controller.updateWebPartner(userId, url, partner))
            return respond(Response.Status.OK);
        return respond(Response.Status.INTERNAL_SERVER_ERROR);
    }

    @DELETE
    @Path("/partner/{url}")
    public Response removeWebPartner(
            @PathParam("url") String url,
<<<<<<< HEAD
            @HeaderParam(value = "X-ICE-Authentication-SessionId") String sessionId) {
=======
            @HeaderParam(AUTHENTICATION_PARAM_NAME) String sessionId) {
>>>>>>> 3a93b296cacb68f217094cf7df86236a73cd323c
        String userId = getUserIdFromSessionHeader(sessionId);
        if (controller.removeWebPartner(userId, url))
            return respond(Response.Status.OK);
        return respond(Response.Status.INTERNAL_SERVER_ERROR);
    }
}
