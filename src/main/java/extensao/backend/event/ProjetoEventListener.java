package extensao.backend.event;

import extensao.backend.entity.Projeto;
import extensao.backend.entity.Oportunidade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.AfterSaveEvent;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

@Component
public class ProjetoEventListener extends AbstractMongoEventListener<Projeto> {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public void onAfterSave(AfterSaveEvent<Projeto> event) {
        Projeto projetoAtualizado = event.getSource();
        
        if (projetoAtualizado.getId() != null) {
            // Sincroniza o objeto 'projeto' incorporado em todas as oportunidades relacionadas
            Query query = new Query(Criteria.where("projetoId").is(projetoAtualizado.getId()));
            Update update = new Update().set("projeto", projetoAtualizado);
            
            mongoTemplate.updateMulti(query, update, Oportunidade.class);
        }
    }
}
