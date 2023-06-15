/* eslint-disable max-len */
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
    const userId = data["userId"];
    const fcmToken = data["fcmToken"];
    const collection = data["collection"];
    await firebase.firestore()
      .collection(collection)
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
          text: "emergencyUpdated",
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

// Função para o dentista enviar aceite
export const sendMessage = functions
  .region("southamerica-east1")
  .runWith({enforceAppCheck: false})
  .https
  .onCall(async (data, context)=>{
    // eslint-disable-next-line max-len
    const fcmToken = data["fcmToken"];
    const dId = data["dentistId"];
    const message = {
      data: {
        text: "accepted",
        id: dId,
      },
      token: fcmToken,
    };
    try {
      await firebase.messaging().send(message);
    } catch (e) {
      console.error("Erro ao enviar a notificação", e);
    }
  });

// Função para mendar mensagem para o dentista de aceite
export const sendMessageToDentist = functions
  .region("southamerica-east1")
  .runWith({enforceAppCheck: false})
  .https
  .onCall(async (data, context)=>{
    const fcmToken = data["fcmToken"];
    const messageType = data["messageType"];
    const uid = data["uid"];
    const message={
      data: {
        text: messageType,
        id: uid,
      },
      token: fcmToken,
    };
    try {
      await firebase.messaging().send(message);
    } catch (e) {
      console.error("Erro ao enviar a mensagem", e);
    }
  });

// Função para mandar mensagem de rating para o socorrista

export const sendRTS = functions
  .region("southamerica-east1")
  .runWith({enforceAppCheck: false})
  .https
  .onCall(async (data, context)=>{
    const fcmToken = data["fcmToken"];
    const messageType= data["messageType"];
    const dId = data["id"];
    const message= {
      data: {
        text: messageType,
        id: dId,
      },
      token: fcmToken,
    };
    await firebase.messaging().send(message);
  });

export const sendTestMessage2 = functions
  .region("southamerica-east1")
  .runWith({enforceAppCheck: false})
  .https
  .onRequest(async (data, context) =>{
    const fcmToken = "fr-_DlKuSZKCzbbO-7HjDk:APA91bHmiK1EhT-vDzJDlHC1B6zbBK8ehjieeqkll0uhHCp_IOruBeeEZYY30TbaYvnVMY8OEGu_fPhMN_L36Ii_Ub2HPA-PtiPKYyl2zwJ4_ViqdmJ4GAqMKw8Zsiim7V7vk_o8T0X_";
    const message = {
      data: {
        text: "Teste",
        id: "TBvzC9u7tYOVv72s0zOIePpHX3C2",
      },
      token: fcmToken,
    };
    await firebase.messaging().send(message);
  });

export const setActions = functions
  .region("southamerica-east1")
  .runWith({enforceAppCheck: false})
  .https
  .onCall(async (data, context) => {
    const docData = data;
    docData.date = admin.firestore.Timestamp.now();

    await firebase.firestore()
      .collection("actions").doc().set(docData);
  });

export const setAddress = functions
  .region("southamerica-east1")
  .runWith({enforceAppCheck: false})
  .https
  .onCall(async (data, context) =>{
    await firebase.firestore()
      .collection("address")
      .doc().set(data);
  });

// mudar retaing - review

// criar localização
export const sendLocation = functions
  .region("southamerica-east1")
  .runWith({enforceAppCheck: false})
  .https
  .onCall(async (data, context)=>{
    const latitude = data["latitude"]
    const longitude = data["longitude"];
    const fcmToken = data["fcmToken"];
    const text = "location";

    const message = {
      data: {
        text: text,
        latitude: latitude,
        longitude: longitude,
      },
      token: fcmToken,
    };

    await firebase.messaging().send(message);
  });

export const sendRating = functions
  .region("southamerica-east1")
  .runWith({enforceAppCheck: false})
  .https
  .onCall(async (data, context) =>{
    const collectionRef = firebase.firestore().collection("reviews");

    const collectionSnapshot = await collectionRef.get();

    if (collectionSnapshot.empty) {
      collectionRef.add({});
    }

    const docData = data;
    docData.date = admin.firestore.Timestamp.now();
    await firebase.firestore().collection("reviews").doc().set(docData);
    return {
      message: "Rating enviado com sucesso!",
    };
  });
