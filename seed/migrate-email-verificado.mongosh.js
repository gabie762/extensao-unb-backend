// Backfill obrigatório ao introduzir emailVerificado em Usuario.
// Sem isso, todo usuário existente fica sem login (emailVerificado desserializa como false).
// Rodar uma vez, logo após o deploy do código que introduz esse campo:
//
// sudo docker exec -i backend-mongo-1 mongosh -u "$MONGO_USER" -p "$MONGO_PASSWORD" --authenticationDatabase admin < seed/migrate-email-verificado.mongosh.js

const targetDb = 'extensao_unb';
const db = db.getSiblingDB(targetDb);

const resultado = db.usuarios.updateMany(
  { emailVerificado: { $exists: false } },
  { $set: { emailVerificado: true } }
);

print(`Usuários atualizados: ${resultado.modifiedCount}`);
