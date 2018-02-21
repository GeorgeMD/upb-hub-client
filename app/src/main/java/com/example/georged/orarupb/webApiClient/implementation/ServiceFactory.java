package com.example.georged.orarupb.webApiClient.implementation;

import com.example.georged.orarupb.utils.interfaces.IServiceEvents;

/**
 * Created by George D on 23-Jun-17.
 */

public class ServiceFactory {

    private static ServiceFactory INSTANCE;

    private String baseUrl;
    private IServiceEvents events;

    private ServiceFactory() { }

    public static ServiceFactory getInstance() {
        return INSTANCE == null ? INSTANCE = new ServiceFactory() : INSTANCE;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public void setEvents(IServiceEvents events) {
        this.events = events;
    }

    private GroupService groupService;
    public GroupService getGroupService() {
        return  groupService == null ? groupService = new GroupService(baseUrl, events) : groupService;
    }

    private StudentService studentService;
    public StudentService getStudentService() {
        return studentService == null ? studentService = new StudentService(baseUrl, events) : studentService;
    }

    private ScheduleService scheduleService;
    public ScheduleService getScheduleService() {
        return scheduleService == null ? scheduleService = new ScheduleService(baseUrl, events) : scheduleService;
    }

    private UtilsService utilsService;
    public UtilsService getUtilsService() {
        return utilsService == null ? utilsService = new UtilsService(baseUrl, events) : utilsService;
    }
}
