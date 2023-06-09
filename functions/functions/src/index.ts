import * as functions from "firebase-functions";
import * as admin from "firebase-admin";

const firebase = admin.initializeApp();

export const emergencyListener = functions
  .region("southamerica-east1")
  .runWith({enforceAppCheck: false})
  .firestore
  .document("/teste_emergencia/{id}")
  .onCreate(async (snapshot, context) => {
    const data = snapshot.data();
    const docId = context.params.id;
    const querySnapshot = await firebase.firestore()
      .collection("dentists")
      .where("availability", "==", true).get();
    const tokens = querySnapshot.docs.map((doc)=>doc.data().fcmToken);
    const message ={
      data: {
        text: data.name,
        id: docId,
      },
      tokens: tokens,
    };
    try {
      await firebase.messaging().sendEachForMulticast(message);
      console.log("Enviada para ${response.successCount} dispositivos.");
    } catch (e) {
      console.error("Erro ao enviar a notificação", e);
    }
  });

export const setUser = functions
  .region("southamerica-east1")
  .runWith({enforceAppCheck: false})
  .https
  .onCall(async (data, context) =>{
    const {userId, ...userData} = data;
    await firebase.firestore()
      .collection("dentists")
      .doc(userId)
      .set(userData);
  });

export const setToken = functions
  .region("southamerica-east1")
  .runWith({enforceAppCheck: false})
  .https
  .onCall(async (data, context) =>{
    const {userId, ...fcmToken} = data;
    await firebase.firestore()
      .collection("dentist")
      .doc(userId)
      .update({"fcmToken": fcmToken});
  });

export const emergencyListener2 = functions
  .region("southamerica-east1")
  .runWith({enforceAppCheck: false})
  .firestore
  .document("/emergencies/{id}")
  .onUpdate(async (change, context) => {
    const newValue = change.after.data();
    const oldValue = change.before.data();
    const docId = context.params.id;
    const newStatus = newValue.state;
    const oldStatus = oldValue.state;

    if (newStatus === "new" && newStatus !== oldStatus) {
      const querySnapshot = await firebase.firestore()
        .collection("dentists")
        .where("availability", "==", true)
        .get();
      const tokens = querySnapshot.docs.map((doc)=>doc.data().fcmToken);
      const message ={
        data: {
          text: newValue.name,
          id: docId,
        },
        tokens: tokens,
      };
      try {
        await firebase.messaging().sendEachForMulticast(message);
        console.log("Enviada para ${response.successCount} dispositivos.");
      } catch (e) {
        console.error("Erro ao enviar a notificação", e);
      }
    } else {
      console.log("O valor de status não foi modificado ");
    }
  });

export const sendMessage = functions
  .region("southamerica-east1")
  .runWith({enforceAppCheck: false})
  .https
  .onRequest(async (data, context)=>{
    // eslint-disable-next-line max-len
    const fcmToken = "cqmeff5FQfGNpW2YPkL3yr:APA91bGG87ayBWtQTLXbo2Mqa2WxoS0WLmFDRtMwfDf-VTejmmcfTfO4k8lozRVDNKYlpolEB8C268uYVg7oiqniICeVzXRaNHWV5nzSFcVddU5Z8s9iJtfs5m7ShEZ53BAA5l9Clcz0";
    const message = {
      data: {
        text: "Teste",
        id: "GgieUDQz4UMUgGVU3aoEAplZ1Zf2",
      },
      token: fcmToken,
    };
    try {
      await firebase.messaging().send(message);
    } catch (e) {
      console.error("Erro ao enviar a notificação", e);
    }
  });

