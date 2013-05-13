package org.jbei.ice.client;

import java.util.ArrayList;
import java.util.HashMap;

import org.jbei.ice.client.entry.view.model.SampleStorage;
import org.jbei.ice.client.exception.AuthenticationException;
import org.jbei.ice.shared.AutoCompleteField;
import org.jbei.ice.shared.ColumnField;
import org.jbei.ice.shared.EntryAddType;
import org.jbei.ice.shared.dto.AccountInfo;
import org.jbei.ice.shared.dto.AccountResults;
import org.jbei.ice.shared.dto.BulkUploadInfo;
import org.jbei.ice.shared.dto.ConfigurationKey;
import org.jbei.ice.shared.dto.MessageInfo;
import org.jbei.ice.shared.dto.NewsItem;
import org.jbei.ice.shared.dto.SampleInfo;
import org.jbei.ice.shared.dto.bulkupload.BulkUploadAutoUpdate;
import org.jbei.ice.shared.dto.bulkupload.PreferenceInfo;
import org.jbei.ice.shared.dto.entry.EntryInfo;
import org.jbei.ice.shared.dto.entry.EntryType;
import org.jbei.ice.shared.dto.entry.SequenceAnalysisInfo;
import org.jbei.ice.shared.dto.folder.FolderDetails;
import org.jbei.ice.shared.dto.group.GroupInfo;
import org.jbei.ice.shared.dto.group.GroupType;
import org.jbei.ice.shared.dto.message.MessageList;
import org.jbei.ice.shared.dto.permission.PermissionInfo;
import org.jbei.ice.shared.dto.search.SearchQuery;
import org.jbei.ice.shared.dto.search.SearchResults;
import org.jbei.ice.shared.dto.user.PreferenceKey;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.SuggestOracle;

public interface RegistryServiceAsync {

    void login(String name, String pass, AsyncCallback<AccountInfo> callback);

    void sessionValid(String sid, AsyncCallback<AccountInfo> callback);

    void logout(String sessionId, AsyncCallback<Boolean> callback);

    void retrieveEntryDetails(String sessionId, long id, String recordId, String url,
            AsyncCallback<EntryInfo> callback) throws AuthenticationException;

    void retrieveEntryTipDetails(String sessionId, String rid, String url, AsyncCallback<EntryInfo> callback)
            throws AuthenticationException;

    void getPermissionSuggestions(SuggestOracle.Request req, AsyncCallback<SuggestOracle.Response> callback);

    void retrieveEntriesForFolder(String sessionId, long folderId, ColumnField sort, boolean asc,
            int start, int limit, AsyncCallback<FolderDetails> callback);

    void retrieveUserEntries(String sid, String userId, ColumnField sort, boolean asc,
            int start, int limit, AsyncCallback<FolderDetails> asyncCallback);

    void performSearch(String sid, SearchQuery searchQuery, boolean isWeb,
            AsyncCallback<SearchResults> callback) throws AuthenticationException;

    void retrieveUserSavedDrafts(String sid, AsyncCallback<ArrayList<BulkUploadInfo>> callback)
            throws AuthenticationException;

    void retrieveDraftsPendingVerification(String sid,
            AsyncCallback<ArrayList<BulkUploadInfo>> callback) throws AuthenticationException;

    void deleteSavedDraft(String sid, long draftId,
            AsyncCallback<BulkUploadInfo> callback) throws AuthenticationException;

    void createSample(String sessionId, SampleStorage sampleStorage, long entryId,
            AsyncCallback<SampleStorage> callback) throws AuthenticationException;

    void retrieveProfileInfo(String sid, String userId, AsyncCallback<AccountInfo> callback)
            throws AuthenticationException;

    void retrieveCollections(String sessionId, AsyncCallback<ArrayList<FolderDetails>> callback);

    void updateFolder(String sid, long folderId, FolderDetails update,
            AsyncCallback<FolderDetails> callback) throws AuthenticationException;

    void createUserCollection(String sid, String name, String description,
            ArrayList<Long> arrayList, AsyncCallback<FolderDetails> callback)
            throws AuthenticationException;

    void moveToUserCollection(String sid, long source, ArrayList<Long> destination,
            ArrayList<Long> entryIds, AsyncCallback<ArrayList<FolderDetails>> callback)
            throws AuthenticationException;

    void addEntriesToCollection(String sid, ArrayList<Long> destination, ArrayList<Long> entryIds,
            AsyncCallback<ArrayList<FolderDetails>> callback) throws AuthenticationException;

    void updateEntry(String sid, EntryInfo info, AsyncCallback<Boolean> callback) throws AuthenticationException;

    void retrieveStorageSchemes(String sessionId, EntryType type,
            AsyncCallback<HashMap<SampleInfo, ArrayList<String>>> callback);

    // news
    void retrieveNewsItems(String sessionId, AsyncCallback<ArrayList<NewsItem>> callback)
            throws AuthenticationException;

    void createNewsItem(String sessionId, NewsItem item, AsyncCallback<NewsItem> callback)
            throws AuthenticationException;

    // bulk import and draft
    void retrieveBulkImport(String sid, long id, int start, int limit, AsyncCallback<BulkUploadInfo> callback)
            throws AuthenticationException;

    void deleteFolder(String sessionId, long folderId, AsyncCallback<FolderDetails> callback);

    void addPermission(String sessionId, PermissionInfo permission,
            AsyncCallback<Boolean> callback) throws AuthenticationException;

    void removePermission(String sessionId, PermissionInfo permissionInfo,
            AsyncCallback<Boolean> callback) throws AuthenticationException;

    void saveSequence(String sessionId, long entry, String sequenceUser, AsyncCallback<Boolean> callback)
            throws AuthenticationException;

    void sendFeedback(String email, String msg, AsyncCallback<Boolean> callback);

    void getConfigurationSetting(String name, AsyncCallback<String> callback);

    void retrieveAccount(String email, AsyncCallback<AccountInfo> callback);

    void createNewAccount(AccountInfo info, boolean sendEmail, AsyncCallback<String> callback);

    void updateAccount(String sid, String email, AccountInfo info,
            AsyncCallback<AccountInfo> callback) throws AuthenticationException;

    void updateAccountPassword(String sid, String email, String password,
            AsyncCallback<Boolean> callback) throws AuthenticationException;

    void handleForgotPassword(String email, String url, AsyncCallback<Boolean> callback)
            throws AuthenticationException;

    void retrieveAllUserAccounts(String sid, int start, int limit, AsyncCallback<AccountResults> callback)
            throws AuthenticationException;

    void removeSequence(String sid, long entryId, AsyncCallback<Boolean> callback)
            throws AuthenticationException;

    void retrieveEntryTraceSequences(String sid, long entryId, AsyncCallback<ArrayList<SequenceAnalysisInfo>> callback)
            throws AuthenticationException;

    void deleteEntryTraceSequences(String sid, long entryId, ArrayList<String> seqId,
            AsyncCallback<ArrayList<SequenceAnalysisInfo>> callback) throws AuthenticationException;

    void deleteEntryAttachment(String sid, String fileId, AsyncCallback<Boolean> callback)
            throws AuthenticationException;

    void retrieveGroups(String sid, GroupType type, AsyncCallback<ArrayList<GroupInfo>> callback)
            throws AuthenticationException;

    void retrieveGroupMembers(String sessionId, GroupInfo info, AsyncCallback<ArrayList<AccountInfo>> callback)
            throws AuthenticationException;

    void setGroupMembers(String sessionId, GroupInfo info, ArrayList<AccountInfo> members,
            AsyncCallback<ArrayList<AccountInfo>> callback) throws AuthenticationException;

    void createNewGroup(String sessionId, GroupInfo info,
            AsyncCallback<GroupInfo> callback) throws AuthenticationException;

    void deleteEntry(String sessionId, EntryInfo info, AsyncCallback<ArrayList<FolderDetails>> callback)
            throws AuthenticationException;

    void createEntry(String sid, EntryInfo info, AsyncCallback<Long> async) throws AuthenticationException;

    void removeFromUserCollection(String sessionId, long source, ArrayList<Long> ids,
            AsyncCallback<FolderDetails> async) throws AuthenticationException;

    void approvePendingBulkImport(String sessionId, long id, AsyncCallback<Boolean> async);

    void submitBulkUploadDraft(String sid, long draftId, AsyncCallback<Boolean> async);

    void getAutoCompleteSuggestion(AutoCompleteField field, SuggestOracle.Request request,
            AsyncCallback<SuggestOracle.Response> async);

    void retrieveSystemSettings(String sid, AsyncCallback<HashMap<String, String>> asyncCallback);

    void retrieveWebOfRegistrySettings(String sid, AsyncCallback<HashMap<String, String>> asyncCallback)
            throws AuthenticationException;

    void setConfigurationSetting(String sid, ConfigurationKey key, String value, AsyncCallback<Boolean> async);

    void setPreferenceSetting(String sid, PreferenceKey key, String value, AsyncCallback<Boolean> async)
            throws AuthenticationException;

    void revertedSubmittedBulkUpload(String sid, long uploadId, AsyncCallback<Boolean> async);

    void retrieveAllVisibleEntrys(String sid, FolderDetails details, ColumnField field, boolean asc, int start,
            int limit, AsyncCallback<FolderDetails> async);

    void retrieveAvailableAccounts(String sessionId, AsyncCallback<ArrayList<AccountInfo>> callback);

    void addWebPartner(String sessionId, String webPartner, AsyncCallback<Boolean> callback);

    void autoUpdateBulkUpload(String sid, BulkUploadAutoUpdate wrapper, EntryAddType addType,
            AsyncCallback<BulkUploadAutoUpdate> callback) throws AuthenticationException;

    void updateBulkUploadPreference(String sid, long bulkUploadId, EntryAddType addType, PreferenceInfo info,
            AsyncCallback<Long> callback) throws AuthenticationException;

    void updateBulkUploadPermissions(String sid, long bulkUploadId, EntryAddType addType,
            ArrayList<PermissionInfo> permissions, AsyncCallback<Long> callback) throws AuthenticationException;

    void retrieveUserPreferences(String sid, ArrayList<PreferenceKey> keys,
            AsyncCallback<HashMap<PreferenceKey, String>> async) throws AuthenticationException;

    void isWebOfRegistriesEnabled(AsyncCallback<Boolean> async);

    void retrieveMessages(String sessionId, int start, int count, AsyncCallback<MessageList> callback)
            throws AuthenticationException;

    void setBulkUploadDraftName(String sessionId, long id, String draftName, AsyncCallback<Boolean> callback)
            throws AuthenticationException;

    void updateGroup(String sessionId, GroupInfo info, AsyncCallback<GroupInfo> async);

    void deleteGroup(String sessionId, GroupInfo info, AsyncCallback<GroupInfo> asyncCallback);

    void removeAccountFromGroup(String sessionId, GroupInfo info, AccountInfo account, AsyncCallback<Boolean> callback);

    void requestEntryTransfer(String sid, ArrayList<Long> ids, ArrayList<String> sites, AsyncCallback<Void> callback);

    void deleteSample(String sessionId, SampleInfo info, AsyncCallback<Boolean> async);

    void retrieveUserGroups(String sessionId, AsyncCallback<ArrayList<GroupInfo>> async);

    void promoteCollection(String sessionId, long id, AsyncCallback<Boolean> async);

    void demoteCollection(String sessionId, long id, AsyncCallback<Boolean> async);

    void sendMessage(String sid, MessageInfo info, AsyncCallback<Boolean> async);

    void markMessageRead(String sessionId, long id, AsyncCallback<Integer> async);
}
