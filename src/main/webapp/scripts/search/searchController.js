'use strict';

angular.module('ice.search.controller', [])
<<<<<<< HEAD
    .controller('SearchController', function ($scope, $http, $cookieStore, $location, Entry, Search, EntryContextUtil, Selection, WebOfRegistries) {
=======
    .controller('SearchController', function ($scope, $http, $cookieStore, $location, Entry, Search, EntryContextUtil,
                                              Selection, WebOfRegistries) {
>>>>>>> 3a93b296cacb68f217094cf7df86236a73cd323c
        $scope.$on("RunSearch", function (event, filters) {
            $scope.searchResults = undefined;
            $scope.searchFilters = filters;
            $scope.currentPage = 1;
            runAdvancedSearch(filters);
        });

        var runAdvancedSearch = function (filters) {
            $scope.loadingSearchResults = true;

<<<<<<< HEAD
            Search().runAdvancedSearch({webSearch:filters.webSearch}, filters,
=======
            Search().runAdvancedSearch({webSearch: filters.webSearch}, filters,
>>>>>>> 3a93b296cacb68f217094cf7df86236a73cd323c
                function (result) {
                    $scope.searchResults = result;
                    $scope.loadingSearchResults = false;
                },
                function (error) {
                    $scope.loadingSearchResults = false;
                    $scope.searchResults = undefined;
                    console.log(error);
                }
            );
        };

<<<<<<< HEAD
        var noFilters = (!$scope.searchFilters || Object.keys($scope.searchFilters).length === 0);

        if (noFilters) {
            $scope.searchFilters = {entryTypes:[], parameters:{}, blastQuery:{}, queryString:""};
            var queryString = $location.search().q;
=======
        $scope.setSearchResultPage = function (pageNo) {
            $scope.searchFilters.parameters.start = (pageNo - 1) * 15;
            $scope.currentPage = pageNo;
            runAdvancedSearch($scope.searchFilters);
        };

        var noFilters = (!$scope.searchFilters || Object.keys($scope.searchFilters).length === 0);

        if (noFilters) {
            $scope.searchFilters = {entryTypes: [], parameters: {}, blastQuery: {}, queryString: ""};
            var queryString = $location.search().q;

>>>>>>> 3a93b296cacb68f217094cf7df86236a73cd323c
            if (queryString !== undefined) {
                $scope.searchFilters.queryString = queryString;
            }
        }

<<<<<<< HEAD
        // filters run advanced search
        $scope.searchFilters.parameters.start = 0;
        $scope.searchFilters.parameters.retrieveCount = 15;
        $scope.searchFilters.parameters.sortField = "RELEVANCE";
        runAdvancedSearch($scope.searchFilters);

        $scope.maxSize = 5;  // number of clickable pages to show in pagination
        $scope.currentPage = 1;

        $scope.setSearchResultPage = function (pageNo) {
            $scope.searchFilters.parameters.start = (pageNo - 1) * 15;
            $scope.currentPage = pageNo;
            runAdvancedSearch($scope.searchFilters);
        };
=======
        var context = EntryContextUtil.getContext();
        if (context) {
            var pageNum = (Math.floor(context.offset / 15)) + 1;
            $scope.setSearchResultPage(pageNum);
        } else {
            $scope.searchFilters.parameters.start = 0;
            $scope.searchFilters.parameters.retrieveCount = 15;
            $scope.searchFilters.parameters.sortField = "RELEVANCE";
            $scope.setSearchResultPage(1);
        }

        $scope.maxSize = 5;  // number of clickable pages to show in pagination
>>>>>>> 3a93b296cacb68f217094cf7df86236a73cd323c

        $scope.getType = function (relScore) {
            if (relScore === undefined)
                return 'info';

            if (relScore >= 70)
                return 'success';
            if (relScore >= 30 && relScore < 70)
                return 'warning';
            if (relScore < 30)
                return 'danger';
            return 'info';
        };

        $scope.tooltipDetails = function (entry) {
            $scope.searchResultToolTip = undefined;
            var sessionId = $cookieStore.get("sessionId");

<<<<<<< HEAD
            Entry(sessionId).tooltip({partId:entry.id},
=======
            Entry(sessionId).tooltip({partId: entry.id},
>>>>>>> 3a93b296cacb68f217094cf7df86236a73cd323c
                function (result) {
                    $scope.searchResultToolTip = result;
                }, function (error) {
                    console.error(error);
                });
        };

        $scope.remoteTooltipDetails = function (result) {
            $scope.searchResultToolTip = undefined;
<<<<<<< HEAD
            WebOfRegistries().getToolTip({partnerId:result.partner.id, entryId:result.entryInfo.id},
=======
            WebOfRegistries().getToolTip({partnerId: result.partner.id, entryId: result.entryInfo.id},
>>>>>>> 3a93b296cacb68f217094cf7df86236a73cd323c
                function (result) {
                    $scope.searchResultToolTip = result;
                }, function (error) {
                    console.error(error);
                });
        };

        $scope.goToEntryDetails = function (entry, index) {
<<<<<<< HEAD
            // this assumes that if the user is able to click on a result then search was successful

            var offset = (($scope.currentPage - 1) * 15) + index;

=======
            if (!$scope.searchFilters.parameters) {
                $scope.searchFilters.parameters = {start: index}
            }

            var offset = (($scope.currentPage - 1) * 15) + index;
>>>>>>> 3a93b296cacb68f217094cf7df86236a73cd323c
            EntryContextUtil.setContextCallback(function (offset, callback) {
                $scope.searchFilters.parameters.start = offset;
                $scope.searchFilters.parameters.retrieveCount = 1;

<<<<<<< HEAD
                Search().runAdvancedSearch({webSearch:$scope.searchFilters.webSearch}, $scope.searchFilters,
                    function (result) {
                        callback(result.results[0].entryInfo.id);
                    },
                    function (error) {
                        console.log(error);
                    }
                );
            }, $scope.searchResults.resultCount, offset, "/search");
=======
                Search().runAdvancedSearch({webSearch: $scope.searchFilters.webSearch}, $scope.searchFilters,
                    function (result) {
                        callback(result.results[0].entryInfo.id);
                    }
                );
            }, $scope.searchResults.resultCount, offset, "/search", $scope.searchResults.sortField);
>>>>>>> 3a93b296cacb68f217094cf7df86236a73cd323c

            $location.path("/entry/" + entry.id);
        };

        //
        // select result entry
        //
        $scope.selectSearchResult = function (entry) {
            Selection.selectEntry(entry);
        };

        $scope.searchEntrySelected = function (entry) {
            return Selection.searchEntrySelected(entry);
        }
    })
<<<<<<< HEAD
    .controller('SearchInputController', function ($scope, $rootScope, $http, $cookieStore, $location) {
        $scope.searchTypes = {all:true, strain:true, plasmid:true, part:true, arabidopsis:true};
=======
    .controller('SearchInputController', function ($scope, $rootScope, $http, $cookieStore, $location, Search) {
        $scope.searchTypes = {all: true, strain: true, plasmid: true, part: true, arabidopsis: true};
>>>>>>> 3a93b296cacb68f217094cf7df86236a73cd323c

        $scope.check = function (selection) {
            var allTrue = true;
            for (var type in $scope.searchTypes) {
                if ($scope.searchTypes.hasOwnProperty(type) && type !== 'all') {
                    if (selection === 'all')
                        $scope.searchTypes[type] = $scope.searchTypes.all;
                    allTrue = (allTrue && $scope.searchTypes[type] === true);
                }
            }
            $scope.searchTypes.all = allTrue;
        };

        var defineQuery = function () {
<<<<<<< HEAD
            var searchQuery = {entryTypes:[], parameters:{start:0, retrieveCount:15, sortField:"RELEVANCE"}, blastQuery:{}};
=======
            var searchQuery = {
                entryTypes: [],
                parameters: {start: 0, retrieveCount: 15, sortField: "RELEVANCE"},
                blastQuery: {}
            };
>>>>>>> 3a93b296cacb68f217094cf7df86236a73cd323c

            // check search types  : {all: false, strain: true, plasmid: false, part: true, arabidopsis: true}
            for (var type in $scope.searchTypes) {
                if ($scope.searchTypes.hasOwnProperty(type) && type !== 'all') {
                    if ($scope.searchTypes[type]) {
                        searchQuery.entryTypes.push(type.toUpperCase());
                    }
                }
            }

            // check blast search type
            if ($scope.blastSearchType) {
                searchQuery.blastQuery.blastProgram = $scope.blastSearchType;
            }

            // check "has ..."
            if ($scope.hasAttachment)
                searchQuery.parameters.hasAttachment = $scope.hasAttachment;

            if ($scope.hasSample)
                searchQuery.parameters.hasSample = $scope.hasSample;

            if ($scope.hasSequence)
                searchQuery.parameters.hasSequence = $scope.hasSequence;

            // bio safety
            if ($scope.bioSafetyLevelOption) {
                searchQuery.bioSafetyOption = $scope.bioSafetyLevelOption == "1" ? "LEVEL_ONE" : "LEVEL_TWO";
            }

            //sequence
            if ($scope.sequenceText) {
                searchQuery.blastQuery.sequence = $scope.sequenceText;
                if (!searchQuery.blastQuery.blastProgram)
                    searchQuery.blastQuery.blastProgram = "BLAST_N";
            }

            searchQuery.queryString = $scope.queryText;
            return searchQuery;
        };

        $scope.search = function (isWebSearch) {
            $scope.searchFilters = defineQuery();
            $scope.searchFilters.webSearch = isWebSearch;

            var searchUrl = "/search";
            if ($location.path().slice(0, searchUrl.length) != searchUrl) {
                // triggers search controller which uses searchfilters to perform search
                $location.path(searchUrl, false);
            } else {
                $scope.$broadcast("RunSearch", $scope.searchFilters);
            }
        };

        $scope.isWebSearch = function () {
            return $scope.searchFilters.webSearch === true;
        };

        $scope.canReset = function () {
            if ($scope.queryText || $scope.sequenceText || $scope.hasSample || $scope.hasSequence || $scope.hasAttachment)
                return true;

            if ($scope.blastSearchType || $scope.bioSafetyLevelOption)
                return true;

            for (var searchType in $scope.searchTypes) {
                if ($scope.searchTypes.hasOwnProperty(searchType)) {
                    if ($scope.searchTypes[searchType] != true)
                        return true;
                }
            }

            return false;
        };

        //
        // resets the search filters to the defaults setting
        //
        $scope.reset = function () {
            $scope.sequenceText = "";
            $scope.queryText = "";
            $location.url($location.path());
            $scope.blastSearchType = "";
            $scope.bioSafetyLevelOption = "";
            $scope.hasSample = false;
            $scope.hasSequence = false;
            $scope.hasAttachment = false;
            for (var searchType in $scope.searchTypes) {
                if ($scope.searchTypes.hasOwnProperty(searchType)) {
                    $scope.searchTypes[searchType] = true;
                }
            }
        };
<<<<<<< HEAD
=======

        $scope.sortResults = function (sortType) {
            sortType = sortType.toUpperCase();

            if (!$scope.searchFilters.parameters) {
                $scope.searchFilters.parameters = {sortAscending: false};
            } else {
                if (sortType === $scope.searchFilters.parameters.sortField) {
                    $scope.searchFilters.parameters.sortAscending = !$scope.searchFilters.parameters.sortAscending;
                } else
                    $scope.searchFilters.parameters.sortAscending = false;
            }

            $scope.searchFilters.parameters.sortField = sortType;
            $scope.searchFilters.parameters.start = 0;
            $scope.loadingSearchResults = true;

            Search().runAdvancedSearch({webSearch: $scope.searchFilters.webSearch}, $scope.searchFilters,
                function (result) {
                    $scope.searchResults = result;
                    $scope.loadingSearchResults = false;
                },
                function (error) {
                    $scope.loadingSearchResults = false;
                    $scope.searchResults = undefined;
                    console.log(error);
                }
            );
        };
>>>>>>> 3a93b296cacb68f217094cf7df86236a73cd323c
    });