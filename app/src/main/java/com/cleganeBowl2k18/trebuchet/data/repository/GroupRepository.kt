package com.cleganeBowl2k18.trebuchet.data.repository

import com.cleganeBowl2k18.trebuchet.data.models.Group
import com.cleganeBowl2k18.trebuchet.data.network.GroupService
import com.cleganeBowl2k18.trebuchet.presentation.view.FakeGroupFactory
import io.reactivex.Observable
import retrofit2.Response

/**
 * Facilitates Group API calls
 */
class GroupRepository(private val groupService: GroupService) {

    fun groupsAvailable(): Observable<List<Group>> = groupService.allGroups

    fun fakeGroupsAvailable(): Observable<List<Group>> {
        var groupFactory : FakeGroupFactory = FakeGroupFactory()

        var group1 = groupFactory.generateGroup("Cat Fans", listOf("Tom", "Bob", "Ian"))
        var group2 = groupFactory.generateGroup("Book Club", listOf("Tammy", "Barbora"))
        var group3 = groupFactory.generateGroup("Trip", listOf("Joseph", "Margaret", "Doug"))
        var group4 = groupFactory.generateGroup("Chad Land", listOf("Chad", "Chad", "Chad"))

        var groupList : List<Group> = listOf(group1, group2, group3, group4)
        return Observable.just(groupList)
    }

    fun createGroup(group: Group): Observable<Group> = groupService.createGroup(group.toGroupCreator())

    fun updateGroup(group: Group): Observable<Group> = groupService.updateGroup(group.toGroupCreator())

    fun deleteGroup(groupId: Long): Observable<Response<Void>> = groupService.deleteGroup(groupId)

    fun getGroup(groupId: Long): Observable<Group> = groupService.getGroup(groupId)
}