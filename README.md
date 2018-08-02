- [Description of the tasks](https://www.dropbox.com/s/77zkczuilbt58bm/CGI%20Java_test_ENG.PDF?dl=0 "Description of the tasks")
- [The origin project to implement functions](https://www.dropbox.com/s/e2n29oojws9gvbi/test_source.zip?dl=0 "The origin code to implement functions")

## Time
|  Stage | Time  |
| ------------ | ------------ |
| Stage 1  | 5 hours  |
| Stage 2    |  30 mins |
| Stage 3    | 1 hour 30 mins  |
| Stage 4    | 3 hours  |


## Stage 1
For the time and date, I used [Bootstrap Form Helpers](http://js.nicdn.de/bootstrap/formhelpers/docs/index.html "Bootstrap Form Helpers") controls. There were 2 challenging stuff, first one was that some icons and symbols weren't showing up. With some debugging it turned out that I didn't had ```glyphicons-halflings-regular``` font in my assets. The second thing was I didn't know how to bind those controls with thymeleaf. The ```th:data-name=${('visitTime')}``` isn't supported and I found on stackoverflow that I need to use ```th:attr="data-name=${('visitTime')}"```.The multiple choice wasn't hard I just used ```select``` and created a list. To store data, I made new entity ```PractitionerEntity``` and made ManyToMany relationship with the ```DentistVisitEntity```.

## Stage 2
This stage was probably the easiest, and I just needed to look how to enumerate list in thymeleaf. To print the result, I used a table.
In the controller
```java
    @GetMapping("/appointments")
    public String showAppointments(Model model){
        model.addAttribute("allAppointments", this.dentistVisitService.listVisits());
        return "appointments";
    }
```
In HTML
```html
<th:block th:each="appointment : ${allAppointments}">
   <tr>
      <td th:text="${appointment.getId()}"/>
      <td th:text="${appointment.getDentistName()}"/>
      <td th:text="${appointment.getPractitioners()}"/>
      <td th:text="${appointment.getVisitDate()}"/>
      <th><a th:href="@{/appointments/{id}(id=${appointment.getId()})}">View</a>
   </tr>
</th:block>
```

## Stage 3
I did a pretty basic search by the dentist's name. Just an input field and by clicking the button it redirects on search?name=input_text page. The results are rendered on the same html page as for the Stage 2 just the list in model is replaced.
```java
@GetMapping("/search")
public String searchByName(@RequestParam(required = true, defaultValue = "", value="name") String dentistName, Model model){
        model.addAttribute("allAppointments", this.dentistVisitService.searchByName(dentistName));
        model.addAttribute("dentistname", dentistName);
        return "appointments";
    }
```
The query for ```searchByName``` is
```sql
SELECT e FROM DentistVisitEntity e WHERE e.dentistName LIKE %(:dentistName)%
```

## Stage 4
- 4.1. To remove an appointment I made a function in DAO
```java
    public void removeById(Long id){
        this.entityManager.createQuery("DELETE FROM DentistVisitEntity e WHERE e.id =:id").setParameter("id", id).executeUpdate();;
    }
```
added a route in the controller
```java
    @GetMapping("/appointments/remove/{id}")
    public String removeAppointment(@PathVariable("id") Long id){
        this.dentistVisitService.removeById(id);

        return "redirect:/appointments";
    }
```
and added a button in HTML to call it
```html
<button th:onclick="'location.href = \'' + @{/appointments/remove/{id}(id=${appointment.getId()})} + '\''" type="button" rel="tooltip" title="Remove"/>
```

- 4.2. The idea for this task is to find the original entity
```java
DentistVisitEntity dentistVisitEntity = this.dentistVisitService.getById(id);
```
change the properties and save by using
```java
this.entityManager.merge(visit);
```

- 4.3. For this task I marked ```visitDate``` column as unique. Then I made a function in DAO
```java
    public Boolean checkDate(Date visitDate){
        Query query = this.entityManager.createQuery("SELECT e FROM DentistVisitEntity e WHERE e.visitDate = (:visitDate)");
        query.setParameter("visitDate", visitDate);
        return query.getResultList().size() == 0;
    }
```
and used it in the controller to check if the date and time is taken, if it's taken then throw an error
```java
if (!this.dentistVisitService.checkDate(calendar.getTime())){
            bindingResult.rejectValue("visitDate", "error.visitDate", "Date and time is already taken!");
            bindingResult.rejectValue("visitTime", "error.visitTime", "Date and time is already taken!");
            return "form";
}
```

## Additional information
As HTML template I used Light [Bootstrap Dashboard](https://www.creative-tim.com/product/light-bootstrap-dashboard "Bootstrap Dashboard").

Interface example
[![Screen 1](https://i.imgur.com/QXmkRwh.png "Screen 1")](https://i.imgur.com/QXmkRwh.png "Screen 1")
[![Screen 2](https://i.imgur.com/EpsaWKo.png "Screen 2")](https://i.imgur.com/EpsaWKo.png "Screen 2")
[![Screen 3](https://i.imgur.com/ibru69Y.png "Screen 3")](https://i.imgur.com/ibru69Y.png "Screen 3")

## Conclusions
The most challenging in this work for me was thymeleaf. I never worked with it before. So far I didn't like it because of the way the bindings in thymeleaf works. In places where you think the solution is obvious - in reality its not, it has many nuances. As for the rest I already had experience: in college we had a project to build students attendance system and this experience helped me a lot to finish this tasks. The stack of technologies was the same except that I was using JSP and the patterns were a little bit different like I used repositories instead of DAO.
