package com.cleganeBowl2k18.trebuchet.data.repository

import com.cleganeBowl2k18.trebuchet.data.entity.Group
import com.cleganeBowl2k18.trebuchet.data.network.GroupService
import io.reactivex.Observable
import retrofit2.Response

/**
 * Created by khersey on 2017-10-15.
 */
class GroupRepository(private val groupService: GroupService) {

    fun groupsAvailable(): Observable<List<Group>> = groupService.allGroups

    fun fakeGroupsAvailable(): Observable<List<Group>> {

    }

    fun createGroup(group: Group): Observable<Group> = groupService.createGroup(group)

    fun updateGroup(group: Group): Observable<Group> = groupService.updateGroup(group)

    fun deleteGroup(groupId: Long): Observable<Response<Void>> = groupService.deleteGroup(groupId)

    fun getGroup(groupId: Long): Observable<Group> = groupService.getGroup(groupId)
}